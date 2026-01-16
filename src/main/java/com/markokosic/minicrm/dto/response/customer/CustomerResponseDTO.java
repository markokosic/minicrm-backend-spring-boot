package com.markokosic.minicrm.dto.response.customer;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type"
)
@JsonSubTypes({
		@JsonSubTypes.Type(value = ConsumerCustomerResponseDTO.class, name = "CONSUMER"),
		@JsonSubTypes.Type(value = CreateBusinessCustomerResponseDTO.class, name = "BUSINESS")
})
public class CustomerResponseDTO {
private Long id;
private String type;
private String status;
private Long tenantId;
}
