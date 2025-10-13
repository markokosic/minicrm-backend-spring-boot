package com.markokosic.minicrm.exception;

import com.markokosic.minicrm.common.ErrorCode;

public class TenantAlreadyExistsException extends RuntimeException {
public TenantAlreadyExistsException() {
	super(ErrorCode.TENANTNAME_INVALID.getKey());
}

public TenantAlreadyExistsException(String message) {
	super(message);
}
}
