package com.markokosic.minicrm.config;

public class TenantContext {
	private static final ThreadLocal<Long> CONTEXT = new ThreadLocal<>();
	public static final Long DEFAULT_TENANT = 9L; // Standard-Tenant für tenant-unabhängige Aufrufe


	public static void setCurrentTenant(Long tenant) {
			CONTEXT.set(tenant);

	}

	public static Long getCurrentTenant() {
		return CONTEXT.get();
	}


	public static void clearCurrentTenant() {
		CONTEXT.remove();
	}

}
