package com.markokosic.minicrm.dto.response.customer;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.markokosic.minicrm.common.CustomerType;
import lombok.Data;

@Data
@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type"
)
@JsonSubTypes({
		@JsonSubTypes.Type(value = CreateConsumerCustomerResponseDTO.class, name = "CONSUMER"),
		@JsonSubTypes.Type(value = CreateBusinessCustomerResponseDTO.class, name = "BUSINESS")
})
public class CreateCustomerResponseDTO {
private Long id;
private CustomerType type;
private String status;
private Long tenantId;
}
