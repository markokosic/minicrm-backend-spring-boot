package com.markokosic.minicrm.modules.customer;

import com.markokosic.minicrm.modules.customer.dto.request.CreateCustomerRequestDTO;
import com.markokosic.minicrm.common.dto.response.ApiResponseDTO;
import com.markokosic.minicrm.modules.customer.dto.response.CustomerResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class CustomerController {

	private final CustomerService customerService;

	//CREATE
	@PostMapping("/customers")
	public ResponseEntity<ApiResponseDTO<CustomerResponseDTO>> createCustomer(@Valid @RequestBody CreateCustomerRequestDTO createCustomerRequest){
		CustomerResponseDTO newCustomer = customerService.createCustomer(createCustomerRequest);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>(true, newCustomer, "Successfully created new customer."));
	};

	//READ
	@GetMapping("/customers")
	public ResponseEntity<ApiResponseDTO<List<CustomerResponseDTO>>> getCustomer(){
		List<CustomerResponseDTO> customers = customerService.getCustomers();
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>(true, customers, "Successfully fetched customers"));
	};

	//UPDATE
	@PutMapping("/customers")
	public ResponseEntity<ApiResponseDTO<CreateCustomerResponseDTO>> getCustomer(@Valid @RequestBody CreateCustomerRequestDTO request){
	};

	//DELETE
//	@DeleteMapping("/customers")
//	public ResponseEntity<ApiResponseDTO<CreateCustomerResponseDTO>> getCustomer(@Valid @RequestBody CreateCustomerRequestDTO request){
//	};

}
