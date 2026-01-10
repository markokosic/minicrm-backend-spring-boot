package com.markokosic.minicrm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDTO {
	private boolean success = false;
	private String message;
	private String errorKey;
	private HttpStatusCode statusCode;
	private Map<String, List<String>> fieldErrors;
	private Instant timestamp;
}
