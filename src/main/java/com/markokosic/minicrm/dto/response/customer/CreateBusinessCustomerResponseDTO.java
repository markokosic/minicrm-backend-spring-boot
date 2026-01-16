package com.markokosic.minicrm.dto.response.customer;

import lombok.Data;

@Data
public class CreateBusinessCustomerResponseDTO extends CustomerResponseDTO {
	private String companyName;
	private String vat;
	private String email;
	private String phone;
	private String website;
}
