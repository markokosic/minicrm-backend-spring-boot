package com.markokosic.minicrm.modules.customer.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.markokosic.minicrm.modules.customer.model.CustomerType;
import lombok.Getter;

@Getter
@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.EXISTING_PROPERTY,
		property = "type",
		visible = true
)
@JsonSubTypes({
		@JsonSubTypes.Type(value = UpdateConsumerCustomerRequestDTO.class, name = "CONSUMER"),
		@JsonSubTypes.Type(value = UpdateBusinessCustomerRequestDTO.class, name = "BUSINESS")
})
public class UpdateCustomerRequestDTO {

	private Long id;
	private CustomerType type;

}
