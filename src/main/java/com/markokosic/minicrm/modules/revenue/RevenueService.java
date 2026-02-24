package com.markokosic.minicrm.modules.revenue;

import com.markokosic.minicrm.modules.driver.service.DriverLookupService;
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

	@Transactional
	public void createDailyRevenue(CreateDailyRevenueRequestDTO request){
		driverLookupService.validateDriverExistsOrThrow(request.driverId());
		Long tenantId = tenantService.getTenantIdFromContextHolder();
		DailyRevenue dailyRevenue = revenueMapper.toEntity(request, tenantId);
		revenueRepository.save(dailyRevenue);
	}

	@Transactional
	public void createDailyRevenuesBulk(CreateDailyRevenueBulkRequestDTO request){
		Long tenantId = tenantService.getTenantIdFromContextHolder();

		List<Long> driverIds = request.dailyRevenueRequests().stream().map(CreateDailyRevenueRequestDTO::driverId).toList();
		driverLookupService.validateAllExistOrThrow(driverIds);

		List<DailyRevenue> entities = request.dailyRevenueRequests().stream()
				.map(dto -> revenueMapper.toEntity(dto, tenantId))
				.toList();

		revenueRepository.saveAll(entities);

		// Statt findById (was ein SELECT auslöst)
		Driver driverProxy = driverRepository.getReferenceById(dto.driverId());

// Jetzt kannst du den Proxy einfach setzen
		entity.setDriver(driverProxy);

		ich muss den Driver aus DB holen und speichern ansonsten

	}

}
