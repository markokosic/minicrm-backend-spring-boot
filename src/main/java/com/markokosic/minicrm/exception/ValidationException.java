package com.markokosic.minicrm.exception;

import com.markokosic.minicrm.common.ApiErrorCode;
import org.springframework.http.HttpStatus;

public class ValidationException extends ApiException {
	public ValidationException(ApiErrorCode errorCode) {
		super(errorCode, HttpStatus.BAD_REQUEST);
	}

	public ValidationException(ApiErrorCode errorCode, HttpStatus status) {
		super(errorCode, status);
	}
}
