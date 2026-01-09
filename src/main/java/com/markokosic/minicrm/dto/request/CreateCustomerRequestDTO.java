package com.markokosic.minicrm.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateCustomerRequestDTO {

	@NotNull(message = "customerTypeId must not be null")
	private Long customerTypeId;

}
