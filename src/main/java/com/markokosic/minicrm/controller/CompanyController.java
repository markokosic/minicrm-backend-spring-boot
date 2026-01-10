package com.markokosic.minicrm.controller;

import com.markokosic.minicrm.dto.request.CreateCompanyRequestDTO;
import com.markokosic.minicrm.dto.response.ApiResponseDTO;
import com.markokosic.minicrm.dto.response.CompanyResponseDTO;
import com.markokosic.minicrm.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class CompanyController {

	private final CompanyService companyService;

	@PostMapping("/companies")
	public ResponseEntity<ApiResponseDTO<CompanyResponseDTO>> createCompany(@Valid @RequestBody CreateCompanyRequestDTO createCompanyRequest){
	CompanyResponseDTO  newCompany =	companyService.createCompany(createCompanyRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>(true, newCompany, "Successfully created company" ));
	}

	@GetMapping("/companies")
	public ResponseEntity<ApiResponseDTO<List<CompanyResponseDTO>>> getAllCompanies() {
		List<CompanyResponseDTO> allCompanies =  companyService.getAllCompanies();
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>(true, allCompanies, "Successfully fetched all companies" ));

	}
}
