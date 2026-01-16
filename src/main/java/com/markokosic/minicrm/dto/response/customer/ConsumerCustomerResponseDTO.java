package com.markokosic.minicrm.dto.response.customer;

import lombok.Data;

@Data
public class ConsumerCustomerResponseDTO extends CustomerResponseDTO {
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
}
