package com.markokosic.minicrm.modules.revenue;

import com.markokosic.minicrm.modules.driver.model.Driver;
import com.markokosic.minicrm.modules.driver.repository.DriverRepository;
import com.markokosic.minicrm.modules.driver.service.DriverLookupService;
import com.markokosic.minicrm.modules.tenant.TenantService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RevenueService {

	private final DriverLookupService driverLookupService;
	private final RevenueRepository revenueRepository;
	private final RevenueMapper revenueMapper;
	private final TenantService tenantService;
	private final DriverRepository driverRepository;

	@Transactional
	public void createDailyRevenue(CreateDailyRevenueRequestDTO request){
		driverLookupService.validateDriverExistsOrThrow(request.driverId());
		Long tenantId = tenantService.getTenantIdFromContextHolder();
		Driver driverProxy = driverRepository.getReferenceById(request.driverId());
		DailyRevenue dailyRevenue = revenueMapper.toEntity(request, tenantId, driverProxy);
		revenueRepository.save(dailyRevenue);
	}

	@Transactional
	public void createDailyRevenuesBulk(List<CreateDailyRevenueRequestDTO> request){
		Long tenantId = tenantService.getTenantIdFromContextHolder();

		Set<Long> driverIds = request.stream().map(CreateDailyRevenueRequestDTO::driverId).collect(Collectors.toSet());
		driverLookupService.validateAllExistOrThrow(driverIds);

		List<DailyRevenue> entities = request.stream()
				.map(dto ->
						{
							Driver driverProxy = driverRepository.getReferenceById(dto.driverId());
							return revenueMapper.toEntity(dto, tenantId, driverProxy);
						}

				)
				.toList();

		revenueRepository.saveAll(entities);
	}

}
