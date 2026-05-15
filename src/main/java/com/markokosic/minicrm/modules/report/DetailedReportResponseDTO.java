package com.markokosic.minicrm.modules.report;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Response object representing a detailed revenues report in the system")
public record DetailedReportResponseDTO(
		String groupLabel,
		String driverName,
		String licensePlate,
		BigDecimal totalRevenue,
		BigDecimal totalKm,
		Long entryCount
) {
}




