package com.markokosic.minicrm.modules.customer;

import com.markokosic.minicrm.common.dto.response.ApiResponseDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.markokosic.minicrm.modules.customer.dto.request.CreateCustomerRequestDTO;
import com.markokosic.minicrm.modules.customer.dto.response.CustomerResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@Slf4j
@RequiredArgsConstructor
public class CustomerController {

	private final CustomerService customerService;

	@PostMapping
	public ResponseEntity<ApiResponseDTO<CustomerResponseDTO>> createCustomer(@Valid @RequestBody CreateCustomerRequestDTO request){
		CustomerResponseDTO newCustomer = customerService.createCustomer(request);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>(true, newCustomer, "Successfully created new customer."));
	};

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseDTO<CustomerResponseDTO>> getCustomer(@PathVariable Long id){
		CustomerResponseDTO customer = customerService.getCustomer(id);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>(true, customer, "Successfully fetched customer"));
	};

	@GetMapping
	public ResponseEntity<ApiResponseDTO<List<CustomerResponseDTO>>> getAllCustomer(){
		List<CustomerResponseDTO> customers = customerService.getAllCustomers();
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>(true, customers, "Successfully fetched customers"));
	};

	@PatchMapping("/{id}")
	public ResponseEntity<ApiResponseDTO<CustomerResponseDTO>> updateCustomer(
			@PathVariable Long id,
			@RequestBody JsonNode requestBody
	) {
		CustomerResponseDTO updatedCustomer = customerService.updateCustomer(id, requestBody);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>(true, updatedCustomer, "Successfully updated customer"));
	};

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponseDTO<CustomerResponseDTO>> updateCustomer(
			@PathVariable Long id
	) {
		 customerService.deleteCustomer(id);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>(true, null, "Successfully deleted customer"));
	};

}
