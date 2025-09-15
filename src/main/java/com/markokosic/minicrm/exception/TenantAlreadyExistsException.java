package com.markokosic.minicrm.exception;

import com.markokosic.minicrm.common.Error;

public class TenantAlreadyExistsException extends RuntimeException {
public TenantAlreadyExistsException() {
	super(Error.TENANTNAME_INVALID.getCode());
}

public TenantAlreadyExistsException(String message) {
	super(message);
}
}
