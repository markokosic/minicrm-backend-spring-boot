package com.markokosic.minicrm.modules.remuneration;

import com.markokosic.minicrm.modules.driver.model.DriverRemunerationConfig;
import com.markokosic.minicrm.modules.driver.model.PercentageShareRemunerationConfig;

import java.math.BigDecimal;
import java.math.RoundingMode;

public non-sealed class PercentageRemunerationCalculator implements IRemunerationCalculator {

	@Override
	public RemunerationSplit calculateRemuneration(BigDecimal revenue, DriverRemunerationConfig config) {
		PercentageShareRemunerationConfig pc = (PercentageShareRemunerationConfig) config;

		BigDecimal factor = pc.getDriverRevenueSharePercentage();
		if (factor != null && factor.compareTo(BigDecimal.ONE) > 0) {
			factor = factor.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
		}

		BigDecimal driverShare = revenue
				.multiply(factor != null ? factor : BigDecimal.ZERO)
				.setScale(2, RoundingMode.HALF_UP);

		// driver will always receive a payment even on bad days
		BigDecimal finalDriverShare = pc.getMinDriverPayout() != null
				? driverShare.max(pc.getMinDriverPayout())
				: driverShare;

		BigDecimal companyShare = revenue.subtract(finalDriverShare);

		return new RemunerationSplit(companyShare, finalDriverShare);
	}
}
