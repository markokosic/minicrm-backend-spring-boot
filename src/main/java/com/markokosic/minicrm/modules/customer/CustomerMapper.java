package com.markokosic.minicrm.modules.customer;

import com.markokosic.minicrm.modules.customer.model.CustomerType;
import com.markokosic.minicrm.modules.customer.dto.response.ConsumerCustomerResponseDTO;
import com.markokosic.minicrm.modules.customer.dto.response.CreateBusinessCustomerResponseDTO;
import com.markokosic.minicrm.modules.customer.dto.response.CustomerResponseDTO;
import com.markokosic.minicrm.modules.customer.model.BusinessCustomer;
import com.markokosic.minicrm.modules.customer.model.ConsumerCustomer;
import com.markokosic.minicrm.modules.customer.model.Customer;
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
