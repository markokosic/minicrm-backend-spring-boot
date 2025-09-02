package com.markokosic.minicrm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDTO {
	private boolean success = false;
	private String message;
	private Integer code;
	private Map<String, List<String>> errors;
}
