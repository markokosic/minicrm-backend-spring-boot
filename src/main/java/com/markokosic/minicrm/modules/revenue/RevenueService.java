package com.markokosic.minicrm.modules.revenue;

import com.markokosic.minicrm.modules.driver.model.Driver;
import com.markokosic.minicrm.modules.driver.model.DriverRemunerationConfig;
import com.markokosic.minicrm.modules.driver.repository.DriverRepository;
import com.markokosic.minicrm.modules.driver.service.DriverLookupService;
import com.markokosic.minicrm.modules.remuneration.RemunerationService;
import com.markokosic.minicrm.modules.remuneration.RemunerationSplit;
import com.markokosic.minicrm.modules.tenant.TenantService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RevenueService {

	private final DriverLookupService driverLookupService;
	private final RevenueRepository revenueRepository;
	private final RevenueMapper revenueMapper;
	private final TenantService tenantService;
	private final DriverRepository driverRepository;
	private final RemunerationService remunerationService;

	@Transactional
	public void createDailyRevenue(CreateDailyRevenueRequestDTO request){
		Driver driver = driverLookupService.validateDriverExistsOrThrow(request.driverId());
		Long tenantId = tenantService.getTenantIdFromContextHolder();

		//save remunerationConfig to dailyRevenue
		DriverRemunerationConfig currentConfig = driver.getRemunerationConfigs().stream()
				.filter(DriverRemunerationConfig::isCurrent)
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("No current remuneration config found"));

		//calculate remuneration splits for company & driver
		RemunerationSplit remunerationSplit = remunerationService.calculateDailyRevenueShare(request.revenue(), currentConfig);

		DailyRevenue dailyRevenue = revenueMapper.toEntity(request, tenantId, driver, currentConfig, remunerationSplit.companyRemuneration(), remunerationSplit.driverRemuneration());
		revenueRepository.save(dailyRevenue);
	}

	@Transactional
	public void createDailyRevenuesBulk(List<CreateDailyRevenueRequestDTO> request){
//		Long tenantId = tenantService.getTenantIdFromContextHolder();
//
//		Set<Long> driverIds = request.stream().map(CreateDailyRevenueRequestDTO::driverId).collect(Collectors.toSet());
//		 driverLookupService.validateAllExistOrThrow(driverIds);
//
//		List<DailyRevenue> dailyRevenues = request.stream()
//				.map(dto ->
//						{
//							Driver driverProxy = driverRepository.getReferenceById(dto.driverId());
//							DriverRemunerationConfig driverRemunerationConfig = driverProxy.getRemunerationConfigs().stream()
//									.filter(DriverRemunerationConfig::isCurrent)
//									.findFirst()
//									.orElseThrow(() -> new IllegalStateException("No current remuneration config found"));
//							return revenueMapper.toEntity(dto, tenantId, driverProxy, driverRemunerationConfig);
//						}
//
//				)
//				.toList();
//
//		revenueRepository.saveAll(dailyRevenues);

	}

}
