package com.markokosic.minicrm.modules.driver.dto.request;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.markokosic.minicrm.modules.remuneration.RemunerationModelType;
import jakarta.validation.constraints.NotNull;

@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.EXISTING_PROPERTY,
		property = "remunerationModelType",
		visible = true
)
@JsonSubTypes({
		@JsonSubTypes.Type(value = CreatePercentageShareRemunerationConfigDTO.class, name = "PERCENTAGE_SHARE"),
		@JsonSubTypes.Type(value = CreateWeeklyFixedRemunerationConfigDTO.class, name = "WEEKLY_FIXED_RATE")
})
public abstract class CreateRemunerationRequestDTO {
	@NotNull(message = "{driver.remunerationModelType.notNull}")
	RemunerationModelType remunerationModelType;
}
