package com.markokosic.minicrm.exception;

import com.markokosic.minicrm.common.ApiErrorCode;

public class TenantAlreadyExistsException extends RuntimeException {
public TenantAlreadyExistsException() {
	super(ApiErrorCode.TENANTNAME_INVALID.getKey());
}

public TenantAlreadyExistsException(String message) {
	super(message);
}
}
