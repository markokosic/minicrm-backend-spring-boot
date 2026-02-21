package com.markokosic.minicrm.modules.drivers;

public class PercentageRemuneration extends RemunerationModel {
	@Override
	public double calculatePay(int sumOfAllRides) {
		return sumOfAllRides * 0.05;
	}
}
