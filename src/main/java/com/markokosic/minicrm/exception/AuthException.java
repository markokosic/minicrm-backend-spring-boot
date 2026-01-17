
package com.markokosic.minicrm.exception;

import com.markokosic.minicrm.common.error.ApiErrorCode;
import org.springframework.http.HttpStatus;

public class AuthException extends ApiException {

	public AuthException(ApiErrorCode errorCode) {
		super(errorCode, HttpStatus.UNAUTHORIZED);
	}

	public AuthException(ApiErrorCode errorCode, HttpStatus status) {
		super(errorCode, status);
	}
}
