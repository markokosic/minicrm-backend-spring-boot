package com.markokosic.minicrm.modules.shift.dto.request;

import com.markokosic.minicrm.modules.shift.model.ShiftEntryCategory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CreateShiftRevenueEntryRequestDTO(
		@NotNull
		ShiftEntryCategory entryCategory,

		Long flatRateTypeId,

		@PositiveOrZero
		BigDecimal revenue,

		@PositiveOrZero
		Long tripCount,

		@PositiveOrZero
		BigDecimal pricePerTrip,

		@PositiveOrZero
		BigDecimal weeklyDriverRent
) {
	public BigDecimal getEffectiveRevenue() {
		if (entryCategory == ShiftEntryCategory.WEEKLY) {
			if (weeklyDriverRent != null) return weeklyDriverRent;
			if (revenue != null) return revenue;
			return BigDecimal.ZERO;
		}
		if (revenue != null) {
			return revenue;
		}
		if (tripCount != null && pricePerTrip != null) {
			return pricePerTrip.multiply(BigDecimal.valueOf(tripCount));
		}
		if (weeklyDriverRent != null) {
			return weeklyDriverRent;
		}
		throw new IllegalArgumentException("Either 'revenue' or ('tripCount' and 'pricePerTrip') or 'weeklyDriverRent' must be provided.");
	}
}
