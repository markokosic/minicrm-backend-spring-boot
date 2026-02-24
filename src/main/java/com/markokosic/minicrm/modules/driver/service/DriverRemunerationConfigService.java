package com.markokosic.minicrm.modules.driver.service;

import com.markokosic.minicrm.modules.driver.model.DriverRemunerationConfig;
import com.markokosic.minicrm.modules.remuneration.RemunerationModelType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DriverRemunerationConfigService {



	class CreateDriverRemunerationConfigDTO {
		private RemunerationModelType remunerationModelType;
		private BigDecimal percentage;
		private BigDecimal fixedAmount;
		private BigDecimal guaranteeAmountPerWeek;
		private LocalDate validFrom;             // default today
	}

	public DriverRemunerationConfig createRemunerationConfig(DriverRemunerationConfig config) {
		return config;
	}

}
