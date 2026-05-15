package com.markokosic.minicrm.modules.report;

import com.markokosic.minicrm.common.I18nService;
import com.markokosic.minicrm.common.dto.response.ApiResponseDTO;
import com.markokosic.minicrm.common.dto.response.PageResponseDTO;
import com.markokosic.minicrm.modules.driver.dto.response.DriverResponseDTO;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reports")
@Slf4j
@RequiredArgsConstructor
public class ReportController {

	private final ReportService reportSerivce;
	private final I18nService i18n;

	//Endpoint welcher mir einen Report generiert anhand Parameter (driverId, carId, dateFrom, dateTo, groupBy MONTH DAY YEAR DRIVER CAR,
	//gib mir in Abhängigkeit meiner Filter immer verschiedene Datensätze zurück
	@GetMapping("/revenue-details")
	public ResponseEntity<ApiResponseDTO<XXXXX>> getRevenueDetails(
			@RequestParam
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			LocalDate dateFrom,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			LocalDate dateTo,
			@RequestParam (required = false) Long driverId,
			@RequestParam (required = false) Long carId,
			@RequestParam GroupBy groupBy
	) {
		DetailedReportResponseDTO detailedReport = reportService.generateReport(dateFrom, dateTo, driverId, carId, groupBy);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>(true, detailedReport, i18n.getMessage("success.fetched")));

	}

}
