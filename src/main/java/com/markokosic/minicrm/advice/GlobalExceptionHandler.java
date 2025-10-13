package com.markokosic.minicrm.advice;

import com.markokosic.minicrm.common.ErrorCode;
import com.markokosic.minicrm.dto.response.ErrorResponseDTO;
import com.markokosic.minicrm.exception.BadCredentialsException;
import com.markokosic.minicrm.exception.ExpiredAuthTokenException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

//	@ExceptionHandler(AuthenticationException.class)
//	public ResponseEntity<ErrorResponseDTO> handleAuthenticationException(AuthenticationException ex) {
//		HttpStatusCode statusCode = HttpStatus.UNAUTHORIZED;
//		ErrorResponseDTO error = ErrorResponseDTO.builder()
//				.statusCode(statusCode)
//				.message(ErrorCode.AUTH_INVALID_CREDENTIALS.getMessage())
//				.errorKey(ErrorCode.AUTH_INVALID_CREDENTIALS.getKey())
//				.timestamp(Instant.now())
//				.build();
//		return ResponseEntity.status(statusCode).body(error);
//	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponseDTO> handleBadCredentials(BadCredentialsException ex) {
		return buildError(ex.getErrorKey(), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<ErrorResponseDTO> handleExpiredJwt(ExpiredJwtException ex) {
		return buildError(ErrorCode.ACCESS_TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED);
	}



	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<ErrorResponseDTO> handleExpiredJwtException(ExpiredAuthTokenException ex) {


		ErrorResponseDTO error = new ErrorResponseDTO();
		error.setSuccess(false);
		error.setMessage(ex.getMessage());
		error.setStatusCode(401);
		return ResponseEntity.status(401).body(error);
	}


	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDTO> handleOtherExceptions(Exception ex) {
		ErrorResponseDTO error = new ErrorResponseDTO();
		error.setSuccess(false);
		error.setMessage("Internal Server Error" + " "  + ex.getMessage());
		return ResponseEntity.status(500).body(error);
	}

	private ResponseEntity<ErrorResponseDTO> buildError(ErrorCode code, HttpStatusCode status) {
		ErrorResponseDTO error = ErrorResponseDTO.builder()
				.statusCode(status)
				.message(code.getMessage())
				.errorKey(code.getKey())
				.timestamp(Instant.now())
				.build();
		return ResponseEntity.status(status).body(error);
	}

}
