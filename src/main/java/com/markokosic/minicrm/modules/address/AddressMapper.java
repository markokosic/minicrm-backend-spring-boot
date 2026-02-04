package com.markokosic.minicrm.modules.address;

import com.markokosic.minicrm.modules.address.dto.request.CreateAddressRequestDTO;
import com.markokosic.minicrm.modules.address.dto.response.AddressResponseDTO;
import com.markokosic.minicrm.modules.address.model.Address;
import com.markokosic.minicrm.modules.customer.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "type", source = "dto.type")
	@Mapping(target = "tenantId", source = "customer.tenantId")
	@Mapping(target = "customer", source = "customer")
	Address toEntity(CreateAddressRequestDTO dto, Customer customer);

	AddressResponseDTO toDto(Address address);


}
