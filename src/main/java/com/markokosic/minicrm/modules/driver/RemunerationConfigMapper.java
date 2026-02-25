package com.markokosic.minicrm.modules.driver;

import com.markokosic.minicrm.modules.driver.dto.request.CreatePercentageShareRemunerationConfigDTO;
import com.markokosic.minicrm.modules.driver.dto.request.CreateRemunerationRequestDTO;
import com.markokosic.minicrm.modules.driver.dto.request.CreateWeeklyFixedRemunerationConfigDTO;
import com.markokosic.minicrm.modules.driver.model.Driver;
import com.markokosic.minicrm.modules.driver.model.DriverRemunerationConfig;
import com.markokosic.minicrm.modules.driver.model.PercentageShareRemunerationConfig;
import com.markokosic.minicrm.modules.driver.model.WeeklyFixedRateRemunerationConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SubclassMapping;

@Mapper(componentModel = "spring")
public interface RemunerationConfigMapper {

	//	@SubclassMapping(source = PercentageShareRemunerationConfig.class, target = CreatePercentageShareRemunerationConfigDTO.class)
	//	@SubclassMapping(source = WeeklyFixedRateRemunerationConfig.class, target = CreateWeeklyFixedRemunerationConfigDTO.class)
	//	void toDto(DriverRemunerationConfig remunerationConfig);

	@SubclassMapping(source = PercentageShareRemunerationConfig.class, target = CreatePercentageShareRemunerationConfigDTO.class)
	@SubclassMapping(source = WeeklyFixedRateRemunerationConfig.class, target = CreateWeeklyFixedRemunerationConfigDTO.class)
	@Mapping(target = "id", ignore = true)
	DriverRemunerationConfig toEntity(CreateRemunerationRequestDTO dto, Long tenantId, Driver driver);

}
