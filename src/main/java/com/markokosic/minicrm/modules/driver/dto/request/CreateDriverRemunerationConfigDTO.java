package com.markokosic.minicrm.modules.driver.dto.request;

import com.markokosic.minicrm.modules.remuneration.RemunerationModelType;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CreateDriverRemunerationConfigDTO(
		@NotNull(message = "{driver.remunerationModelType.notNull}")
		RemunerationModelType remunerationModelType,

		@PositiveOrZero(message = "{driver.dailyMinPayout.invalid}")
		Integer dailyMinPayout,

		@DecimalMin(value = "0.0", inclusive = false, message = "{driver.percentage.invalid}")
		@DecimalMax(value = "100.0", message = "{driver.percentage.invalid}")
		BigDecimal percentage
) {}
