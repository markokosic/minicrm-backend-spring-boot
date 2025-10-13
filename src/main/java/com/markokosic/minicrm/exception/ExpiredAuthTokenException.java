package com.markokosic.minicrm.exception;

import com.markokosic.minicrm.common.ErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Access token expired")
public class ExpiredAuthTokenException extends RuntimeException {
	public ExpiredAuthTokenException(){
		super(String.valueOf(ErrorCodes.ACCESS_TOKEN_EXPIRED));
	}

	public ExpiredAuthTokenException(String message) {
		super(message);
	}
}
