package com.markokosic.minicrm.modules.remuneration;

import com.markokosic.minicrm.modules.driver.model.DriverRemunerationConfig;
import com.markokosic.minicrm.modules.driver.model.PercentageShareRemunerationConfig;
import com.markokosic.minicrm.modules.driver.model.WeeklyFixedRateRemunerationConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class RemunerationService {

	public RemunerationSplit calculateDailyRevenueShare(BigDecimal dailyRevenue, DriverRemunerationConfig config) {

		IRemunerationCalculator calculator = switch (config) {
			case WeeklyFixedRateRemunerationConfig c -> new WeeklyFixedRateRemunerationCalculator();
			case PercentageShareRemunerationConfig c -> new PercentageRemunerationCalculator();
			default -> throw new IllegalStateException("Unexpected value: " + config);
		};

		return calculator.calculateRemuneration(dailyRevenue, config);

	}

}
