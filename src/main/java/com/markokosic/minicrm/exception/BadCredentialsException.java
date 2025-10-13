package com.markokosic.minicrm.exception;

import com.markokosic.minicrm.common.ApiErrorCode;

public class BadCredentialsException extends RuntimeException {

	private final ApiErrorCode errorKey;

	public BadCredentialsException() {
		super(ApiErrorCode.AUTH_BAD_CREDENTIALS.getMessage());
		this.errorKey = ApiErrorCode.AUTH_BAD_CREDENTIALS;
	}

	public ApiErrorCode getErrorKey() {
		return errorKey;
	}
}
