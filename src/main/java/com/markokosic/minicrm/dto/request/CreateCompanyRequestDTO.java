package com.markokosic.minicrm.dto.request;

import com.markokosic.minicrm.common.ErrorCodes;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateCompanyRequestDTO {

	@NotBlank(message = ErrorCodes.NOT_BLANK)
	private String name;

	@NotBlank(message = ErrorCodes.NOT_BLANK)
	private String vat;

	@Email
	@NotBlank(message = ErrorCodes.NOT_BLANK)
	private String email;

	@NotBlank(message = ErrorCodes.NOT_BLANK)
	private String phone;

	@NotBlank(message = ErrorCodes.NOT_BLANK)
	private String website;


}
