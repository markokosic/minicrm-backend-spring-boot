package com.markokosic.minicrm.mapper;

import com.markokosic.minicrm.dto.response.customer.CreateBusinessCustomerResponseDTO;
import com.markokosic.minicrm.dto.response.customer.CreateConsumerCustomerResponseDTO;
import com.markokosic.minicrm.dto.response.customer.CreateCustomerResponseDTO;
import com.markokosic.minicrm.model.customer.BusinessCustomer;
import com.markokosic.minicrm.model.customer.Customer;
import com.markokosic.minicrm.model.customer.ConsumerCustomer;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassMapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

	@SubclassMapping(source = ConsumerCustomer.class, target = CreateConsumerCustomerResponseDTO.class)
	@SubclassMapping(source = BusinessCustomer.class, target = CreateBusinessCustomerResponseDTO.class)
	CreateCustomerResponseDTO toResponseDTO(Customer customer);
}
