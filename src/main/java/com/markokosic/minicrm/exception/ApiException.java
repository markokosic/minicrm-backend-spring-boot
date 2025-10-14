package com.markokosic.minicrm.exception;

import com.markokosic.minicrm.common.ApiErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {
	private final HttpStatus status;
	private final ApiErrorCode errorCode;

	public ApiException(ApiErrorCode errorCode) {
		super(errorCode.getMessage());
		this.status = HttpStatus.INTERNAL_SERVER_ERROR;
		this.errorCode = errorCode;
	}


	public ApiException(ApiErrorCode errorCode, HttpStatus status) {
		super(errorCode.getMessage());
		this.status = status;
		this.errorCode = errorCode;
	}

}
