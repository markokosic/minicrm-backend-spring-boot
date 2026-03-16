package com.markokosic.minicrm.modules.revenue;

import com.markokosic.minicrm.modules.driver.model.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RevenueMapper {

//	@Mapping(target = "driver", expression = "java(driverRepository.getReferenceById(dto.driverId()))")
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "driver", source = "driver")
	DailyRevenue toEntity(CreateDailyRevenueRequestDTO dto,
						  Long tenantId, Driver driver);
}
