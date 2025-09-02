package com.markokosic.minicrm.dto.request;

import com.markokosic.minicrm.common.ErrorCodes;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {
	@Email(message = ErrorCodes.EMAIL_INVALID)
	@NotBlank(message = "email-required")
	private String email;

	@NotBlank(message = "password-required")
	private String password;
}