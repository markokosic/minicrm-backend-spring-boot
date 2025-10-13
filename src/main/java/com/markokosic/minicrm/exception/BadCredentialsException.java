package com.markokosic.minicrm.exception;

import com.markokosic.minicrm.common.ErrorCode;

public class BadCredentialsException extends RuntimeException {

	private final ErrorCode errorKey;

	public BadCredentialsException() {
		super(ErrorCode.AUTH_BAD_CREDENTIALS.getMessage());
		this.errorKey = ErrorCode.AUTH_BAD_CREDENTIALS;
	}

	public ErrorCode getErrorKey() {
		return errorKey;
	}
}
