package com.markokosic.minicrm.modules.driver.model;

import jakarta.persistence.Column;
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

	@Column(name="weekly_fixed_rate")
	private BigDecimal weeklyFixedRate;
}


