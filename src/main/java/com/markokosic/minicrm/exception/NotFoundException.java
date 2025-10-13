package com.markokosic.minicrm.exception;

import com.markokosic.minicrm.common.ApiErrorCode;
import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiException {

	public NotFoundException(ApiErrorCode errorCode) {
		super(errorCode, HttpStatus.NOT_FOUND);
	}


	public NotFoundException(ApiErrorCode errorCode, HttpStatus status) {
		super(errorCode, status);
	}
}
