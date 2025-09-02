package com.markokosic.minicrm.config;

import lombok.NonNull;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
 public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver, HibernatePropertiesCustomizer {

	//hier request einfügen? oder value von wo anders holen
	private static final ThreadLocal<String> currentTenant = new ThreadLocal<>();
	private static final String DEFAULT_TENANT = "public"; // Standard-Tenant für tenant-unabhängige Aufrufe


	public void setCurrentTenant(String tenant) {
		if (tenant == null) {
			currentTenant.remove();
		} else {
			currentTenant.set(tenant);
		}
	}

	@Override
	public String resolveCurrentTenantIdentifier() {
		String tenantId = currentTenant.get();
		if (tenantId == null) {
			return "9";
		}
		return tenantId;
	}

	public void clearCurrentTenant() {
		currentTenant.remove();
	}


	@Override
	public void customize(Map<String, Object> hibernateProperties) {
		hibernateProperties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this);
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return false;
	}

}
