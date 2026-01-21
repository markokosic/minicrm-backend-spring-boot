package com.markokosic.minicrm.modules.tenant;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TenantService {

	public Long getTenantIdFromContextHolder() {
		Long tenantId = TenantContextHolder.getTenantId();
		if (tenantId == null) {
			throw new IllegalStateException("No tenant ID found in context");
		}
		return tenantId;
	}
}
