package com.markokosic.minicrm.dto.response.customer;

import lombok.Data;

@Data
public class CreateBusinessCustomerResponseDTO extends CreateCustomerResponseDTO {
	private String name;
	private String vat;
	private String email;
	private String phone;
	private String website;
}
