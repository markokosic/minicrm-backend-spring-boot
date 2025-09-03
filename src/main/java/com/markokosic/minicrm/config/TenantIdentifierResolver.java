package com.markokosic.minicrm.config;

import lombok.NonNull;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
 public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver, HibernatePropertiesCustomizer {


	@Override
	public Long resolveCurrentTenantIdentifier() {
		Long tenantId = TenantContext.getCurrentTenant();
		if (tenantId == null) {
			return TenantContext.DEFAULT_TENANT;
		}
		System.out.println(tenantId + "BLABLABLABLA");

		return tenantId;
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
