package com.markokosic.minicrm.mapper;

import com.markokosic.minicrm.common.CustomerType;
import com.markokosic.minicrm.dto.response.customer.ConsumerCustomerResponseDTO;
import com.markokosic.minicrm.dto.response.customer.CreateBusinessCustomerResponseDTO;
import com.markokosic.minicrm.dto.response.customer.CustomerResponseDTO;
import com.markokosic.minicrm.model.customer.BusinessCustomer;
import com.markokosic.minicrm.model.customer.ConsumerCustomer;
import com.markokosic.minicrm.model.customer.Customer;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.SubclassMapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

	@SubclassMapping(source = ConsumerCustomer.class, target = ConsumerCustomerResponseDTO.class)
	@SubclassMapping(source = BusinessCustomer.class, target = CreateBusinessCustomerResponseDTO.class)
	CustomerResponseDTO toResponseDTO(Customer customer);

	@AfterMapping
	default void setCustomerType(@MappingTarget CustomerResponseDTO dto, Customer customer) {
		if (customer instanceof BusinessCustomer) {
			dto.setType(CustomerType.BUSINESS.getCode());
		} else if (customer instanceof ConsumerCustomer) {
			dto.setType(CustomerType.CONSUMER.getCode());
		}
	}
}
