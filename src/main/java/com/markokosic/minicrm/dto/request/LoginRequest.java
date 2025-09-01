package com.markokosic.minicrm.dto.request;

import com.markokosic.minicrm.common.ErrorCodes;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
	@Email(message = ErrorCodes.EMAIL_INVALID)
	@NotBlank(message = "email-required")
	@Size(min = 10, message = "email-too-short")
	private String email;

	@NotBlank(message = "password-required")
	@Size(min = 8, message = "password-too-short")
	@Size(max = 50, message = "username-too-long")
	private String password;
}