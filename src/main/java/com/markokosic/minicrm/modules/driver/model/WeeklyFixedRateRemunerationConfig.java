package com.markokosic.minicrm.modules.driver.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@DiscriminatorValue("WEEKLY_FIXED_RATE")
public class WeeklyFixedRateRemunerationConfig extends DriverRemunerationConfig {
	private BigDecimal weeklyFixedRate;

//	@PrePersist
//	protected void onCreate() {
//		this.setRemunerationModelType(RemunerationModelType.WEEKLY_FIXED_RATE);
//	}

}
