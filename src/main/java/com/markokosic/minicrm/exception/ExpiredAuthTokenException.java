package com.markokosic.minicrm.exception;

import com.markokosic.minicrm.common.ErrorCodes;

public class ExpiredAuthTokenException extends RuntimeException {
	public ExpiredAuthTokenException(){
		super(String.valueOf(ErrorCodes.ACCESS_TOKEN_EXPIRED));
	}

	public ExpiredAuthTokenException(String message) {
		super(message);
	}
}
