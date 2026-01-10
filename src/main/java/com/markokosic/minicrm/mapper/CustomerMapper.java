package com.markokosic.minicrm.mapper;

import com.markokosic.minicrm.dto.request.CreateCompanyRequestDTO;
import com.markokosic.minicrm.dto.request.CreateCustomerRequestDTO;
import com.markokosic.minicrm.dto.response.CreateCustomerResponseDTO;
import com.markokosic.minicrm.model.Customer;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
	@Mapping(target="tenantId", expression = "java(tenantId)")
	Customer toEntity(CreateCustomerRequestDTO dto, @Context Long tenantId);
	CreateCompanyRequestDTO toCompanyDTO(CreateCustomerRequestDTO customerDTO);


	CreateCustomerResponseDTO toResponseDTO(Customer customer);
}
