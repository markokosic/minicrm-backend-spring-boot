package com.markokosic.minicrm.modules.driver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.markokosic.minicrm.common.dto.response.PageResponseDTO;
import com.markokosic.minicrm.common.error.ApiErrorCode;
import com.markokosic.minicrm.exception.NotFoundException;
import com.markokosic.minicrm.modules.driver.DriverMapper;
import com.markokosic.minicrm.modules.driver.dto.request.CreateDriverRequestDTO;
import com.markokosic.minicrm.modules.driver.dto.request.UpdateDriverRequestDTO;
import com.markokosic.minicrm.modules.driver.dto.response.DriverResponseDTO;
import com.markokosic.minicrm.modules.driver.model.Driver;
import com.markokosic.minicrm.modules.driver.model.DriverStatus;
import com.markokosic.minicrm.modules.driver.repository.DriverRepository;
import com.markokosic.minicrm.modules.tenant.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DriverService {

	private final TenantService tenantService;
	private final DriverMapper driverMapper;
	private final DriverRepository driverRepository;
	private final ObjectMapper objectMapper;
	private final DriverLookupService driverLookupService;

	@Transactional
	public DriverResponseDTO createDriver(CreateDriverRequestDTO request ) {
		Long tenantId = tenantService.getTenantIdFromContextHolder();
		Driver customer = driverMapper.toEntity(request, tenantId);
		driverRepository.save(customer);
		return driverMapper.toDto(customer);
	}

	public PageResponseDTO<DriverResponseDTO> getAllDrivers(Pageable pageable ) {
		return getDriversByTenant(pageable);
	}

	public  DriverResponseDTO getDriverById(Long id ) {
		return driverMapper.toDto(driverLookupService.findById(id));
	}

	@Transactional
	public DriverResponseDTO updateDriver(Long id, UpdateDriverRequestDTO requestBody) {

		Driver driver = driverLookupService.findById(id);

		driverMapper.updateEntityFromDto(requestBody, driver);

		driverRepository.save(driver);
		return driverMapper.toDto(driver);

	}

	@Transactional
	public void deleteDriver(Long id) {
		Driver customer = validateDriverDeletion(id);
		customer.setStatus(DriverStatus.DELETED);
	}


	private Driver validateDriverDeletion(Long id) {
		Driver driver = driverLookupService.findById(id);

		if (DriverStatus.DELETED.equals(driver.getStatus())) {
			throw new NotFoundException(ApiErrorCode.Driver_NOT_FOUND);
		}

//		if (hasActiveOrders(driver.getId())) {
//			throw new ConflictException(ApiErrorCode.CUSTOMER_HAS_ACTIVE_ORDERS);
//		}

		return driver;
	}


	private PageResponseDTO<DriverResponseDTO> getDriversByTenant(Pageable pageable) {
		Long tenantId = tenantService.getTenantIdFromContextHolder();
		Page<DriverResponseDTO> page = driverRepository.findAllByTenantIdAndStatus(tenantId, DriverStatus.ACTIVE, pageable).map(driverMapper::toDto);

		return PageResponseDTO.from(page);
	}


}
