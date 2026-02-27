package com.markokosic.minicrm.modules.driver;

import com.markokosic.minicrm.modules.driver.dto.request.CreatePercentageShareRemunerationConfigDTO;
import com.markokosic.minicrm.modules.driver.dto.request.CreateRemunerationRequestDTO;
import com.markokosic.minicrm.modules.driver.dto.request.CreateWeeklyFixedRemunerationConfigDTO;
import com.markokosic.minicrm.modules.driver.model.Driver;
import com.markokosic.minicrm.modules.driver.model.DriverRemunerationConfig;
import com.markokosic.minicrm.modules.driver.model.PercentageShareRemunerationConfig;
import com.markokosic.minicrm.modules.driver.model.WeeklyFixedRateRemunerationConfig;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RemunerationConfigMapper {

	//	@SubclassMapping(source = PercentageShareRemunerationConfig.class, target = CreatePercentageShareRemunerationConfigDTO.class)
	//	@SubclassMapping(source = WeeklyFixedRateRemunerationConfig.class, target = CreateWeeklyFixedRemunerationConfigDTO.class)
	//	void toDto(DriverRemunerationConfig remunerationConfig);

//	@SubclassMapping(source = PercentageShareRemunerationConfig.class, target = CreatePercentageShareRemunerationConfigDTO.class)
//	@SubclassMapping(source = WeeklyFixedRateRemunerationConfig.class, target = CreateWeeklyFixedRemunerationConfigDTO.class)
//	@SubclassMapping(source = CreatePercentageShareRemunerationConfigDTO.class, target = PercentageShareRemunerationConfig.class)
//	@SubclassMapping(source = CreateWeeklyFixedRemunerationConfigDTO.class, target = WeeklyFixedRateRemunerationConfig.class)
//	@Mapping(target = "id", ignore = true)
//	DriverRemunerationConfig toEntity(CreateRemunerationRequestDTO dto, Long tenantId, Driver driver);


	default DriverRemunerationConfig toEntity(
			CreateRemunerationRequestDTO dto,
			Long tenantId,
			Driver driver
	) {
		if (dto instanceof CreatePercentageShareRemunerationConfigDTO percentageDto) {
			return toPercentageShareEntity(percentageDto, tenantId, driver);
		} else if (dto instanceof CreateWeeklyFixedRemunerationConfigDTO weeklyDto) {
			return toWeeklyFixedEntity(weeklyDto, tenantId, driver);
		}
		throw new IllegalArgumentException("Unknown DTO type: " + dto.getClass());
	}

	@Mapping(target = "id", ignore = true)
//	@Mapping(target = "driver", ignore = true)
	@Mapping(target = "tenantId", expression = "java(tenantId)")
	@Mapping(target = "isCurrent", ignore = true)
	@Mapping(target = "validFrom", ignore = true)
	@Mapping(target = "validUntil", ignore = true)
	@Mapping(target = "revenueSharePercentage", source = "percentage")
	@Mapping(target = "minPayout", source = "minPayout")
	PercentageShareRemunerationConfig toPercentageShareEntity(
			CreatePercentageShareRemunerationConfigDTO dto,
			@Context Long tenantId,
			@Context Driver driver
	);

	@Mapping(target = "id", ignore = true)
//	@Mapping(target = "driver", ignore = true)
	@Mapping(target = "tenantId", expression = "java(tenantId)")
	@Mapping(target = "isCurrent", ignore = true)
	@Mapping(target = "validFrom", ignore = true)
	@Mapping(target = "validUntil", ignore = true)
	@Mapping(target = "weeklyFixedRate", source = "weeklyRate")
	WeeklyFixedRateRemunerationConfig toWeeklyFixedEntity(
			CreateWeeklyFixedRemunerationConfigDTO dto,
			@Context Long tenantId,
			@Context Driver driver
	);


}
