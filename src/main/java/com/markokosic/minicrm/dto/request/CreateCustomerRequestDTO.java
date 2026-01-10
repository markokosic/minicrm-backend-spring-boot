package com.markokosic.minicrm.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateCustomerRequestDTO {

	@NotNull(message = "customerTypeId must not be null")
	private Long customerTypeId;

	@NotBlank
	private String name;

	@Email
	private String email;
	private String phone;

	// Optionale Company-Felder
	private String vat;
	private String website;

	// Optionale Person-Felder
	private String firstName;
	private String lastName;

	// Optionale Adresse für Index
	private String primaryStreet;
	private String primaryCity;
	private String primaryZip;
	private String primaryCountry;


}
