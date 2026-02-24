package com.markokosic.minicrm.modules.remuneration;

public class PercentageRemunerationCalculator implements IRemunerationCalculator {

	@Override
	public double calculateRemuneration(int sumOfAllRides) {
		return sumOfAllRides * 0.05;
	}
}
