package com.markokosic.minicrm.exception;


import com.markokosic.minicrm.common.ErrorCodes;

public class BadCredentialsException extends RuntimeException {
	public BadCredentialsException() {
		super(String.valueOf(ErrorCodes.AUTH_INVALID_CREDENTIALS));
	}
	public BadCredentialsException(String message) {
		super(message);
	}
}