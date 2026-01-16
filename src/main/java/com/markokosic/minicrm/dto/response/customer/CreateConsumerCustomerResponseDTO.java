package com.markokosic.minicrm.dto.response.customer;

import lombok.Data;

@Data
public class CreateConsumerCustomerResponseDTO extends CreateCustomerResponseDTO {
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
}
