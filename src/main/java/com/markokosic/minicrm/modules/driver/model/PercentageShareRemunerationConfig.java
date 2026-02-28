package com.markokosic.minicrm.modules.driver.model;

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
	private BigDecimal revenueSharePercentage;

	private BigDecimal minDriverPayout;

//	@PrePersist
//	protected void onCreate() {
//		this.setRemunerationModelType(RemunerationModelType.PERCENTAGE_SHARE);
//	}

}
