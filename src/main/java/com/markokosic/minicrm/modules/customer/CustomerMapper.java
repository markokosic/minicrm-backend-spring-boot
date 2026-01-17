package com.markokosic.minicrm.modules.customer;

import com.markokosic.minicrm.modules.customer.dto.request.UpdateBusinessCustomerRequestDTO;
import com.markokosic.minicrm.modules.customer.dto.request.UpdateConsumerCustomerRequestDTO;
import com.markokosic.minicrm.modules.customer.dto.response.ConsumerCustomerResponseDTO;
import com.markokosic.minicrm.modules.customer.dto.response.CreateBusinessCustomerResponseDTO;
import com.markokosic.minicrm.modules.customer.dto.response.CustomerResponseDTO;
import com.markokosic.minicrm.modules.customer.model.BusinessCustomer;
import com.markokosic.minicrm.modules.customer.model.ConsumerCustomer;
import com.markokosic.minicrm.modules.customer.model.Customer;
import com.markokosic.minicrm.modules.customer.model.CustomerType;
import org.mapstruct.*;

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

	// ✅ Custom mapping methoden für PATCH
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "tenantId", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	ConsumerCustomer updateConsumerCustomer(
			@MappingTarget ConsumerCustomer customer,
			UpdateConsumerCustomerRequestDTO dto
	);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "tenantId", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	BusinessCustomer updateBusinessCustomer(
			@MappingTarget BusinessCustomer customer,
			UpdateBusinessCustomerRequestDTO dto
	);
}
