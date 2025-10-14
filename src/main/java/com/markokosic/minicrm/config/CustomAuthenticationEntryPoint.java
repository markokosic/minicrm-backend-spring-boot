package com.markokosic.minicrm.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.markokosic.minicrm.common.ApiErrorCode;
import com.markokosic.minicrm.dto.response.ErrorResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.time.Instant;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public     void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException{
		log.info("Responding with unauthorized error. Message - {}", authException.getMessage());

		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		ErrorResponseDTO error = ErrorResponseDTO.builder()
				.success(false)
				.message(ApiErrorCode.AUTH_TOKEN_EXPIRED.getMessage())
				.errorKey(ApiErrorCode.AUTH_TOKEN_EXPIRED.getKey())
				.statusCode(HttpStatus.UNAUTHORIZED)
				.timestamp(Instant.now())
				.build();

		response.getWriter().write(new ObjectMapper().writeValueAsString(error));

	};


}
