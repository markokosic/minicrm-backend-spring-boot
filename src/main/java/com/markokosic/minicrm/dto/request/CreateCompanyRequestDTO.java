package com.markokosic.minicrm.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateCompanyRequestDTO {

	@NotBlank(message = "not-blank")
	private String name;

	@NotBlank(message = "not-blank")
	private String vat;

	@Email
	@NotBlank(message = "not-blank")
	private String email;

	@NotBlank(message = "not-blank")
	private String phone;

	@NotBlank(message = "not-blank")
	private String website;


}
