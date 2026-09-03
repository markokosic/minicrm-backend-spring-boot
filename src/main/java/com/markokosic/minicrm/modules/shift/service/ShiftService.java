package com.markokosic.minicrm.modules.shift.service;

import com.markokosic.minicrm.common.dto.response.PageResponseDTO;
import com.markokosic.minicrm.exception.ForbiddenException;
import com.markokosic.minicrm.exception.ResourceNotFoundException;
import com.markokosic.minicrm.modules.car.CarRepository;
import com.markokosic.minicrm.modules.car.model.Car;
import com.markokosic.minicrm.modules.driver.model.Driver;
import com.markokosic.minicrm.modules.driver.model.DriverRemunerationConfig;
import com.markokosic.minicrm.modules.driver.repository.DriverRepository;
import com.markokosic.minicrm.modules.remuneration.RemunerationService;
import com.markokosic.minicrm.modules.remuneration.RemunerationSplit;
import com.markokosic.minicrm.modules.shift.ShiftMapper;
import com.markokosic.minicrm.modules.shift.ShiftRevenueEntryMapper;
import com.markokosic.minicrm.modules.shift.dto.request.CreateMyShiftRequestDTO;
import com.markokosic.minicrm.modules.shift.dto.request.CreateShiftRequestDTO;
import com.markokosic.minicrm.modules.shift.dto.request.CreateShiftRevenueEntryRequestDTO;
import com.markokosic.minicrm.modules.shift.dto.request.UpdateShiftRequestDTO;
import com.markokosic.minicrm.modules.shift.dto.request.UpdateShiftRevenueEntryRequestDTO;
import com.markokosic.minicrm.modules.shift.dto.response.ShiftResponseDTO;
import com.markokosic.minicrm.modules.flatratetype.model.FlatRateType;
import com.markokosic.minicrm.modules.flatratetype.repository.FlatRateTypeRepository;
import com.markokosic.minicrm.modules.shift.model.Shift;
import com.markokosic.minicrm.modules.shift.model.ShiftEntryCategory;
import com.markokosic.minicrm.modules.shift.model.ShiftRevenueEntry;
import com.markokosic.minicrm.modules.shift.model.ShiftSettlement;
import com.markokosic.minicrm.modules.shift.model.ShiftStatus;
import com.markokosic.minicrm.modules.shift.repository.ShiftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.markokosic.minicrm.modules.shift.util.ShiftValidationUtils;

@Service
@RequiredArgsConstructor
public class ShiftService {

	private final DriverRepository driverRepository;
	private final CarRepository carRepository;
	private final FlatRateTypeRepository flatRateTypeRepository;
	private final ShiftRepository shiftRepository;
	private final RemunerationService remunerationService;
	private final ShiftMapper shiftMapper;
	private final ShiftRevenueEntryMapper shiftRevenueEntryMapper;

	@Transactional
	public ShiftResponseDTO createShift(CreateShiftRequestDTO request) {
		ShiftValidationUtils.validateShiftParameters(
				request.odometerStart(),
				request.odometerEnd(),
				request.shiftStart(),
				request.shiftEnd()
		);

		Driver driver = driverRepository.findById(request.driverId())
				.orElseThrow(() -> new ResourceNotFoundException("domain.driver.not_found"));

		Car car = carRepository.findById(request.carId())
				.orElseThrow(() -> new ResourceNotFoundException("domain.car.not_found"));

		ShiftStatus status = request.status() != null ? request.status() : ShiftStatus.APPROVED;
		Shift shift = shiftMapper.toShiftEntity(request, driver, car, status);

		for (CreateShiftRevenueEntryRequestDTO revenueReq : request.revenues()) {
			ShiftRevenueEntry entry = buildNewRevenueEntry(
					shift,
					driver,
					revenueReq.entryCategory(),
					revenueReq.flatRateTypeId(),
					revenueReq.revenue(),
					revenueReq.tripCount(),
					revenueReq.pricePerTrip(),
					revenueReq.weeklyDriverRent()
			);
			shift.addRevenueEntry(entry);
		}

		ensureWeeklyRentEntryIfApplicable(shift, driver);
		updateShiftSettlement(shift);
		Shift saved = shiftRepository.save(shift);
		return shiftMapper.toDto(saved);
	}

	@Transactional
	public ShiftResponseDTO updateShift(Long id, UpdateShiftRequestDTO request) {
		ShiftValidationUtils.validateShiftParameters(
				request.odometerStart(),
				request.odometerEnd(),
				request.shiftStart(),
				request.shiftEnd()
		);

		Shift shift = shiftRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("domain.shift.not_found"));

		updateShiftMetadata(shift, request);
		syncRevenueEntries(shift, request.revenues());
		ensureWeeklyRentEntryIfApplicable(shift, shift.getDriver());
		updateShiftSettlement(shift);

		Shift saved = shiftRepository.save(shift);
		return shiftMapper.toDto(saved);
	}

	private void updateShiftMetadata(Shift shift, UpdateShiftRequestDTO request) {
		if (request.carId() != null && (shift.getCar() == null || !request.carId().equals(shift.getCar().getId()))) {
			Car car = carRepository.findById(request.carId())
					.orElseThrow(() -> new ResourceNotFoundException("domain.car.not_found"));
			shift.setCar(car);
		}
		shift.setOdometerStart(request.odometerStart());
		shift.setOdometerEnd(request.odometerEnd());
		shift.setShiftStart(request.shiftStart());
		shift.setShiftEnd(request.shiftEnd());
	}

	private void syncRevenueEntries(Shift shift, List<UpdateShiftRevenueEntryRequestDTO> revenueRequests) {
		deleteRemovedRevenueEntries(shift, revenueRequests);
		processRevenueEntries(shift, revenueRequests);
	}

	private void deleteRemovedRevenueEntries(Shift shift, List<UpdateShiftRevenueEntryRequestDTO> revenueRequests) {
		Set<Long> requestIds = revenueRequests.stream()
				.map(UpdateShiftRevenueEntryRequestDTO::id)
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());

		shift.getRevenues().removeIf(entry -> entry.getId() != null && !requestIds.contains(entry.getId()));
	}

	private void processRevenueEntries(Shift shift, List<UpdateShiftRevenueEntryRequestDTO> revenueRequests) {
		for (UpdateShiftRevenueEntryRequestDTO revenueReq : revenueRequests) {
			if (revenueReq.id() != null) {
				updateExistingRevenueEntry(shift, revenueReq);
			} else {
				addNewRevenueEntry(shift, revenueReq);
			}
		}
	}

	private void updateExistingRevenueEntry(Shift shift, UpdateShiftRevenueEntryRequestDTO request) {
		ShiftRevenueEntry entry = shift.getRevenues().stream()
				.filter(e -> request.id().equals(e.getId()))
				.findFirst()
				.orElseThrow(() -> new ResourceNotFoundException("domain.shift_revenue_entry.not_found"));

		if (entry.getEntryCategory() == ShiftEntryCategory.WEEKLY) {
			BigDecimal rent = request.weeklyDriverRent() != null ? request.weeklyDriverRent() : request.revenue();
			if (rent == null) {
				rent = BigDecimal.ZERO;
			}
			entry.setWeeklyDriverRent(rent);
			entry.setRevenue(rent);
			entry.setCompanyRemuneration(rent);
			entry.setDriverRemuneration(BigDecimal.ZERO);
			return;
		}

		BigDecimal effectivePricePerTrip = calculateEffectivePricePerTrip(request.pricePerTrip(), entry.getFlatRateType());
		BigDecimal effectiveRevenue = calculateEffectiveRevenue(request.revenue(), request.tripCount(), effectivePricePerTrip, request.weeklyDriverRent());
		RemunerationSplit split = remunerationService.calculateRemunerationSplit(effectiveRevenue, entry.getRemunerationConfig());

		entry.setRevenue(effectiveRevenue);
		entry.setPricePerTrip(effectivePricePerTrip);
		entry.setTripCount(request.tripCount());
		entry.setCompanyRemuneration(split.companyRemuneration());
		entry.setDriverRemuneration(split.driverRemuneration());
	}

	private void addNewRevenueEntry(Shift shift, UpdateShiftRevenueEntryRequestDTO request) {
		ShiftEntryCategory category = request.entryCategory() != null ? request.entryCategory() : ShiftEntryCategory.REGULAR;
		ShiftRevenueEntry newEntry = buildNewRevenueEntry(
				shift,
				shift.getDriver(),
				category,
				request.flatRateTypeId(),
				request.revenue(),
				request.tripCount(),
				request.pricePerTrip(),
				request.weeklyDriverRent()
		);
		shift.addRevenueEntry(newEntry);
	}

	private ShiftRevenueEntry buildNewRevenueEntry(
			Shift shift,
			Driver driver,
			ShiftEntryCategory category,
			Long flatRateTypeId,
			BigDecimal requestedRevenue,
			Long tripCount,
			BigDecimal requestedPricePerTrip,
			BigDecimal requestedWeeklyDriverRent
	) {
		FlatRateType flatRateType = null;
		if (flatRateTypeId != null) {
			flatRateType = flatRateTypeRepository.findById(flatRateTypeId)
					.orElseThrow(() -> new ResourceNotFoundException("domain.flat_rate_type.not_found"));
		}

		DriverRemunerationConfig config = driver.getRemunerationConfigForEntry(category, flatRateType);
		if (config == null) {
			throw new IllegalStateException("No valid remuneration config found for driver " + driver.getId() + " and category " + category);
		}

		if (category == ShiftEntryCategory.WEEKLY) {
			BigDecimal rent = requestedWeeklyDriverRent != null ? requestedWeeklyDriverRent : requestedRevenue;
			if (rent == null) {
				rent = BigDecimal.ZERO;
			}
			return shiftRevenueEntryMapper.toEntity(
					shift,
					config,
					null,
					ShiftEntryCategory.WEEKLY,
					rent,
					null,
					null,
					rent,
					new RemunerationSplit(rent, BigDecimal.ZERO)
			);
		}

		BigDecimal effectivePricePerTrip = calculateEffectivePricePerTrip(requestedPricePerTrip, flatRateType);
		BigDecimal effectiveRevenue = calculateEffectiveRevenue(requestedRevenue, tripCount, effectivePricePerTrip, requestedWeeklyDriverRent);
		RemunerationSplit split = remunerationService.calculateRemunerationSplit(effectiveRevenue, config);

		return shiftRevenueEntryMapper.toEntity(
				shift,
				config,
				flatRateType,
				category,
				effectiveRevenue,
				effectivePricePerTrip,
				tripCount,
				null,
				split
		);
	}

	private void ensureWeeklyRentEntryIfApplicable(Shift shift, Driver driver) {
		if (driver == null) {
			return;
		}
		boolean hasWeeklyConfig = driver.getActiveRemunerationConfigs().stream()
				.anyMatch(c -> c.getType() == com.markokosic.minicrm.modules.remuneration.RemunerationModelType.WEEKLY_FIXED_RATE);
		if (hasWeeklyConfig) {
			boolean hasWeeklyEntry = shift.getRevenues() != null && shift.getRevenues().stream()
					.anyMatch(e -> e.getEntryCategory() == ShiftEntryCategory.WEEKLY);
			if (!hasWeeklyEntry) {
				ShiftRevenueEntry weeklyEntry = buildNewRevenueEntry(
						shift,
						driver,
						ShiftEntryCategory.WEEKLY,
						null,
						BigDecimal.ZERO,
						null,
						null,
						BigDecimal.ZERO
				);
				shift.addRevenueEntry(weeklyEntry);
			}
		}
	}

	private BigDecimal calculateEffectivePricePerTrip(BigDecimal requestedPricePerTrip, FlatRateType flatRateType) {
		if (flatRateType != null && flatRateType.getDefaultPrice() != null) {
			return flatRateType.getDefaultPrice();
		}
		return requestedPricePerTrip;
	}

	private BigDecimal calculateEffectiveRevenue(BigDecimal requestedRevenue, Long tripCount, BigDecimal effectivePricePerTrip, BigDecimal requestedWeeklyDriverRent) {
		if (requestedRevenue != null) {
			return requestedRevenue;
		} else if (tripCount != null && effectivePricePerTrip != null) {
			return effectivePricePerTrip.multiply(BigDecimal.valueOf(tripCount));
		} else if (requestedWeeklyDriverRent != null) {
			return requestedWeeklyDriverRent;
		}
		throw new IllegalArgumentException("Either 'revenue' or ('tripCount' and 'pricePerTrip') or 'weeklyDriverRent' must be provided.");
	}

	@Transactional(readOnly = true)
	public PageResponseDTO<ShiftResponseDTO> getAllShifts(Long driverId, LocalDateTime dateFrom, LocalDateTime dateTo, Pageable pageable) {
		Page<ShiftResponseDTO> page = shiftRepository.findAllFiltered(driverId, dateFrom, dateTo, pageable).map(shiftMapper::toDto);
		return PageResponseDTO.from(page);
	}

	@Transactional(readOnly = true)
	public ShiftResponseDTO getShiftById(Long id) {
		Shift shift = shiftRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("domain.shift.not_found"));
		return shiftMapper.toDto(shift);
	}

	@Transactional
	public void deleteShift(Long id) {
		Shift shift = shiftRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("domain.shift.not_found"));
		shiftRepository.delete(shift);
	}

	@Transactional(readOnly = true)
	public PageResponseDTO<ShiftResponseDTO> 	getMyShifts(Long userId, Pageable pageable) {
		Driver driver = driverRepository.findByUserId(userId)
				.orElseThrow(() -> new ResourceNotFoundException("domain.driver.not_found"));
		return getAllShifts(driver.getId(), null, null, pageable);
	}

	@Transactional(readOnly = true)
	public ShiftResponseDTO getMyShiftById(Long userId, Long shiftId) {
		Driver driver = driverRepository.findByUserId(userId)
				.orElseThrow(() -> new ResourceNotFoundException("domain.driver.not_found"));

		Shift shift = shiftRepository.findById(shiftId)
				.orElseThrow(() -> new ResourceNotFoundException("domain.shift.not_found"));

		if (!shift.getDriver().getId().equals(driver.getId())) {
			throw new ResourceNotFoundException("domain.shift.not_found");
		}

		return shiftMapper.toDto(shift);
	}

	@Transactional
	public ShiftResponseDTO createMyShift(Long userId, CreateMyShiftRequestDTO request) {
		Driver driver = driverRepository.findByUserId(userId)
				.orElseThrow(() -> new ResourceNotFoundException("domain.driver.not_found"));

		CreateShiftRequestDTO internalRequest = new CreateShiftRequestDTO(
				driver.getId(),
				request.carId(),
				request.odometerStart(),
				request.odometerEnd(),
				request.shiftStart(),
				request.shiftEnd(),
				ShiftStatus.PENDING,
				request.revenues()
		);

		return createShift(internalRequest);
	}

	@Transactional
	public ShiftResponseDTO approveShift(Long id) {
		Shift shift = shiftRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("domain.shift.not_found"));
		shift.setStatus(ShiftStatus.APPROVED);
		updateShiftSettlement(shift);
		return shiftMapper.toDto(shift);
	}

	@Transactional
	public ShiftResponseDTO rejectShift(Long id) {
		Shift shift = shiftRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("domain.shift.not_found"));
		shift.setStatus(ShiftStatus.REJECTED);
		return shiftMapper.toDto(shift);
	}

	@Transactional
	public ShiftResponseDTO updateMyShift(Long userId, Long shiftId, UpdateShiftRequestDTO request) {
		ShiftValidationUtils.validateShiftParameters(
				request.odometerStart(),
				request.odometerEnd(),
				request.shiftStart(),
				request.shiftEnd()
		);

		Driver driver = driverRepository.findByUserId(userId)
				.orElseThrow(() -> new ResourceNotFoundException("domain.driver.not_found"));

		Shift shift = shiftRepository.findById(shiftId)
				.orElseThrow(() -> new ResourceNotFoundException("domain.shift.not_found"));

		if (!shift.getDriver().getId().equals(driver.getId())) {
			throw new ResourceNotFoundException("domain.shift.not_found");
		}

		if (shift.getStatus() != ShiftStatus.PENDING) {
			throw new ForbiddenException("domain.shift.cannot_modify_approved");
		}

		updateShiftMetadata(shift, request);
		syncRevenueEntries(shift, request.revenues());
		updateShiftSettlement(shift);

		Shift saved = shiftRepository.save(shift);
		return shiftMapper.toDto(saved);
	}

	@Transactional
	public void deleteMyShift(Long userId, Long shiftId) {
		Driver driver = driverRepository.findByUserId(userId)
				.orElseThrow(() -> new ResourceNotFoundException("domain.driver.not_found"));

		Shift shift = shiftRepository.findById(shiftId)
				.orElseThrow(() -> new ResourceNotFoundException("domain.shift.not_found"));

		if (!shift.getDriver().getId().equals(driver.getId())) {
			throw new ResourceNotFoundException("domain.shift.not_found");
		}

		if (shift.getStatus() != ShiftStatus.PENDING) {
			throw new ForbiddenException("domain.shift.cannot_modify_approved");
		}

		shiftRepository.delete(shift);
	}

	private void updateShiftSettlement(Shift shift) {
		BigDecimal totalRev = BigDecimal.ZERO;
		BigDecimal totalDriver = BigDecimal.ZERO;
		BigDecimal totalCompany = BigDecimal.ZERO;
		DriverRemunerationConfig primaryConfig = null;

		if (shift.getRevenues() != null) {
			for (ShiftRevenueEntry entry : shift.getRevenues()) {
				if (entry.getRevenue() != null) {
					totalRev = totalRev.add(entry.getRevenue());
				}
				if (entry.getDriverRemuneration() != null) {
					totalDriver = totalDriver.add(entry.getDriverRemuneration());
				}
				if (entry.getCompanyRemuneration() != null) {
					totalCompany = totalCompany.add(entry.getCompanyRemuneration());
				}
				if (primaryConfig == null && entry.getRemunerationConfig() != null) {
					primaryConfig = entry.getRemunerationConfig();
				}
			}
		}

		if (primaryConfig == null && shift.getDriver() != null) {
			primaryConfig = shift.getDriver().getCurrentRemunerationConfig();
		}

		ShiftSettlement settlement = shift.getSettlement();
		if (settlement == null) {
			settlement = ShiftSettlement.builder()
					.shift(shift)
					.tenantId(shift.getTenantId())
					.remunerationConfig(primaryConfig)
					.totalRevenue(totalRev)
					.driverRemuneration(totalDriver)
					.companyRemuneration(totalCompany)
					.settledAt(LocalDateTime.now())
					.build();
			shift.setSettlement(settlement);
		} else {
			settlement.setRemunerationConfig(primaryConfig);
			settlement.setTotalRevenue(totalRev);
			settlement.setDriverRemuneration(totalDriver);
			settlement.setCompanyRemuneration(totalCompany);
			settlement.setSettledAt(LocalDateTime.now());
		}
	}
}
