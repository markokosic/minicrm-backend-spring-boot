package com.markokosic.minicrm.modules.report;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ReportService {

	@Transactional(readOnly = true)
	public DetailedReportResponseDTO generateReport(
			LocalDate dateFrom,
			LocalDate dateTo,
			Long driverId,
			Long carId,
			GroupBy groupBy
	) {

	}

	public void findRevenues(FilterParams params){

	}
}
