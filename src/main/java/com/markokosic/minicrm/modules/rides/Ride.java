package com.markokosic.minicrm.modules.rides;

import com.markokosic.minicrm.modules.driver.model.DriverRemunerationConfig;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Ride {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "remuneration_config_id", nullable = false)
	private DriverRemunerationConfig remunerationConfig;

}
