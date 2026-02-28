package com.markokosic.minicrm.modules.revenue;

import com.markokosic.minicrm.modules.driver.model.Driver;
import com.markokosic.minicrm.modules.driver.model.DriverRemunerationConfig;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RevenueMapper {

//	@Mapping(target = "driver", expression = "java(driverRepository.getReferenceById(dto.driverId()))")
	@Mapping(target = "tenantId", expression = "java(tenantId)")
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "driver", source = "driver")
	@Mapping(target = "remunerationConfig", source = "remunerationConfig")
	DailyRevenue toEntity(
			CreateDailyRevenueRequestDTO dto,
			@Context Long tenantId,
			Driver driver,
			DriverRemunerationConfig remunerationConfig
	);
}
