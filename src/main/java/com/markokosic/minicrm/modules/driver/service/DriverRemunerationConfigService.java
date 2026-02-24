package com.markokosic.minicrm.modules.driver.service;

import com.markokosic.minicrm.modules.driver.dto.request.CreateDriverRequestDTO;
import com.markokosic.minicrm.modules.driver.model.DriverRemunerationConfig;
import com.markokosic.minicrm.modules.remuneration.RemunerationModelType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DriverRemunerationConfigService {

	Controller bekommt CreateDriverRequestDTO .
	DriverService ruft createRemunerationConfig auf und gibt DTO + Driver weiter.
	DriverRemunerationConfigService baut aus CreateDriverRemunerationConfigDTO die Entity, setzt validFrom / validUntil usw.
	Config wird in DB gespeichert (Repository).
	DriverService setzt die Config auf den Driver (z. B. driver.addRemunerationConfig(config))
	Driver wird gespeichert.

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
