package com.markokosic.minicrm.modules.driver.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public class CreatePercentageShareRemunerationConfigDTO{

	@PositiveOrZero(message = "{driver.dailyMinPayout.invalid}")
	Integer minPayout;

	@DecimalMin(value = "0.0", inclusive = false, message = "{driver.percentage.invalid}")
	@DecimalMax(value = "100.0", message = "{driver.percentage.invalid}")
	BigDecimal percentage;
}
