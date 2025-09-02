package com.markokosic.minicrm.advice;

import com.markokosic.minicrm.dto.response.ErrorResponseDTO;
import com.markokosic.minicrm.exception.BadCredentialsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex,
			HttpHeaders headers,
			HttpStatusCode status,
			WebRequest request) {

		ErrorResponseDTO errorResponse = new ErrorResponseDTO();
		Map<String, List<String>> fieldErrors = new HashMap<>();

		ex.getBindingResult().getFieldErrors().forEach((error) -> {
			fieldErrors.computeIfAbsent(error.getField(), v -> new ArrayList<>()).add(error.getDefaultMessage());
		});

		errorResponse.setSuccess(false);
		errorResponse.setMessage("Invalid fields");
		errorResponse.setCode(400);
		errorResponse.setErrors(fieldErrors);

		return  ResponseEntity.status(401).body(errorResponse);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponseDTO> handleBadCredentials(BadCredentialsException ex) {
		ErrorResponseDTO error = new ErrorResponseDTO();
		error.setSuccess(false);
		error.setMessage(ex.getMessage());
		error.setCode(401);
		error.setErrors(Map.of());
		return ResponseEntity.status(401).body(error);
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ErrorResponseDTO> handleAuthenticationException(AuthenticationException ex) {
		ErrorResponseDTO error = new ErrorResponseDTO();
		error.setSuccess(false);
		error.setMessage("Authentication failed");
		error.setCode(401);
		error.setErrors(Map.of());
		return ResponseEntity.status(401).body(error);
	}


//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<ErrorResponseDTO> handleOtherExceptions(Exception ex) {
//		ErrorResponseDTO error = new ErrorResponseDTO();
//		error.setSuccess(false);
//		error.setMessage("Internal Server Error" + " "  + ex.getMessage());
//		error.setCode(500);
//		error.setErrors(Map.of());
//		return ResponseEntity.status(500).body(error);
//	}
}
