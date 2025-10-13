package com.markokosic.minicrm.exception;

import com.markokosic.minicrm.common.ApiErrorCode;
import org.springframework.http.HttpStatus;

public class BusinessException extends ApiException {

	public BusinessException(ApiErrorCode errorCode) {
		super(errorCode, HttpStatus.CONFLICT);
	}

	public BusinessException(ApiErrorCode errorCode, HttpStatus status) {
		super(errorCode, status);
	}
	}
}
