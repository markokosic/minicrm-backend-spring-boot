package com.markokosic.minicrm.controller;

import com.markokosic.minicrm.dto.request.customer.CreateCustomerRequestDTO;
import com.markokosic.minicrm.dto.response.ApiResponseDTO;
import com.markokosic.minicrm.dto.response.customer.CreateCustomerResponseDTO;
import com.markokosic.minicrm.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class CustomerController {

	private final CustomerService customerService;

	@PostMapping("/customers")
	public ResponseEntity<ApiResponseDTO<CreateCustomerResponseDTO>> createCustomer(@Valid @RequestBody CreateCustomerRequestDTO createCustomerRequest){
		CreateCustomerResponseDTO newCustomer = customerService.createCustomer(createCustomerRequest);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>(true, newCustomer, "Successfully created new customer."));
	};
}
