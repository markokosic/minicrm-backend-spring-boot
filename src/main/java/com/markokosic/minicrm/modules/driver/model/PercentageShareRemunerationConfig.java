package com.markokosic.minicrm.modules.driver.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@DiscriminatorValue("PERCENTAGE_SHARE")
public class PercentageShareRemunerationConfig extends DriverRemunerationConfig {

	@DecimalMin(value = "0.0", inclusive = false, message = "{driver.percentage.invalid}")
	@DecimalMax(value = "100.0", message = "{driver.percentage.invalid}")
	@Column(name="revenue_share_percentage",precision = 10, scale = 2)
	private BigDecimal revenueSharePercentage;

	@DecimalMin(value = "0.0", message = "{driver.minPayout.negative}")
	@Column(name="min_payout", precision = 10, scale = 2)
	private BigDecimal minDriverPayout;


}
