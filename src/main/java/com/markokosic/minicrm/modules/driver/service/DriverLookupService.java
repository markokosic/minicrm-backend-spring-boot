package com.markokosic.minicrm.modules.driver.service;

import com.markokosic.minicrm.common.error.ApiErrorCode;
import com.markokosic.minicrm.exception.NotFoundException;
import com.markokosic.minicrm.modules.driver.model.Driver;
import com.markokosic.minicrm.modules.driver.repository.DriverRepository;
import com.markokosic.minicrm.modules.tenant.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DriverLookupService {
	private final DriverRepository driverRepository;
	private final TenantService tenantService;

	public Driver findById(Long id) {
		Long tenantId = tenantService.getTenantIdFromContextHolder();
		return driverRepository.findByIdAndTenantId(id, tenantId).orElseThrow(() -> new NotFoundException(ApiErrorCode.Driver_NOT_FOUND));
	}
}
