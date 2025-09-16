package com.markokosic.minicrm.mapper;

import com.markokosic.minicrm.dto.request.CreateCompanyRequestDTO;
import com.markokosic.minicrm.model.Company;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
	@Mapping(target = "tenantId", expression = "java(tenantId)")
	Company toEntity(CreateCompanyRequestDTO dto, @Context Long tenantId);


	CreateCompanyRequestDTO toDto(Company company);
}
