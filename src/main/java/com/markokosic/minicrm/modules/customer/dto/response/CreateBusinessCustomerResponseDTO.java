package com.markokosic.minicrm.modules.customer.dto.response;

import lombok.Data;

@Data
public class CreateBusinessCustomerResponseDTO extends CustomerResponseDTO {
	private String companyName;
	private String vat;
	private String email;
	private String phone;
	private String website;
}
