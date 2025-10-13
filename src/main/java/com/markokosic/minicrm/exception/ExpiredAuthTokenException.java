package com.markokosic.minicrm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Access token expired")
public class ExpiredAuthTokenException extends RuntimeException {
	public ExpiredAuthTokenException(){
		super("token expired");
	}

	public ExpiredAuthTokenException(String message) {
		super(message);
	}
}
