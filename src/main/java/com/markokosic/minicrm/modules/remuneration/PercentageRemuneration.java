package com.markokosic.minicrm.modules.remuneration;

public class PercentageRemuneration implements IRemunerationCalculator {

	@Override
	public double calculateRemuneration(int sumOfAllRides) {
		return sumOfAllRides * 0.05;
	}
}
