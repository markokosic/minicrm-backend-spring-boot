package com.markokosic.minicrm.modules.driver.service;

import com.markokosic.minicrm.modules.driver.RemunerationConfigMapper;
import com.markokosic.minicrm.modules.driver.dto.request.CreateRemunerationRequestDTO;
import com.markokosic.minicrm.modules.driver.model.Driver;
import com.markokosic.minicrm.modules.driver.model.DriverRemunerationConfig;
import com.markokosic.minicrm.modules.driver.repository.DriverRemunerationConfigRepository;
import com.markokosic.minicrm.modules.tenant.TenantService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverRemunerationConfigService {
	private final TenantService tenantService;
	private final RemunerationConfigMapper configMapper;
	private final DriverRemunerationConfigRepository configRepository;
private final DriverLookupService driverLookupService;

	@Transactional
	public DriverRemunerationConfig createRemunerationConfig(CreateRemunerationRequestDTO dto, Long driverId) {
		Driver driver = driverLookupService.validateDriverExistsOrThrow(driverId);

		Long tenantId = tenantService.getTenantIdFromContextHolder();
		DriverRemunerationConfig newConfig = configMapper.toEntity(dto, tenantId, driver);
		return configRepository.save(newConfig);
	}

}
