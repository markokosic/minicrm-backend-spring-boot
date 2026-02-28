package com.markokosic.minicrm.modules.remuneration;

import com.markokosic.minicrm.modules.driver.model.DriverRemunerationConfig;
import com.markokosic.minicrm.modules.driver.model.PercentageShareRemunerationConfig;

import java.math.BigDecimal;
import java.math.RoundingMode;

public non-sealed class PercentageRemunerationCalculator implements IRemunerationCalculator {

	@Override
	public RemunerationSplit calculateRemuneration(BigDecimal revenue, DriverRemunerationConfig config) {
		PercentageShareRemunerationConfig pc = (PercentageShareRemunerationConfig) config;

		BigDecimal driverPercent = pc.getRevenueSharePercentage();

		//prüfung einbauen ob minPayout kleiner ist als die driverRevenue wenn ja dann erhlält der Fahrer minPayout

		//  (Revenue * Percent) / 100
		BigDecimal driverShare = revenue
				.multiply(driverPercent)
				.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);


		BigDecimal companyShare = revenue.subtract(driverShare);

		return new RemunerationSplit(companyShare, driverShare);
	}
}
