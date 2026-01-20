package com.markokosic.minicrm.modules.customer;

import com.markokosic.minicrm.common.dto.response.ApiResponseDTO;
import com.markokosic.minicrm.modules.customer.dto.request.CreateCustomerRequestDTO;
import com.markokosic.minicrm.modules.customer.dto.request.UpdateCustomerRequestDTO;
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

	//CREATE
	@PostMapping
	public ResponseEntity<ApiResponseDTO<CustomerResponseDTO>> createCustomer(@Valid @RequestBody CreateCustomerRequestDTO request){
		CustomerResponseDTO newCustomer = customerService.createCustomer(request);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>(true, newCustomer, "Successfully created new customer."));
	};

	//READ
	@GetMapping
	public ResponseEntity<ApiResponseDTO<List<CustomerResponseDTO>>> getCustomer(){
		List<CustomerResponseDTO> customers = customerService.getCustomers();
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>(true, customers, "Successfully fetched customers"));
	};

	//UPDATE
	@PatchMapping("/{id}")
	public ResponseEntity<ApiResponseDTO<CustomerResponseDTO>> updateCustomer(@Valid @RequestBody UpdateCustomerRequestDTO request){
		System.out.println(request.toString());
		CustomerResponseDTO updatedCustomer = customerService.updateCustomer(request);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>(true, updatedCustomer, "Successfully updated customer"));
	};

	//Folgende Probleme:
	//1. Wie verbinde ich den UpdateCustomerRequestDTO mit dem passenden CustomerType, ohne das mir das frontend die Info zusenden muss
	//2. Wie prüfe ich, ob die gesendeten Felder vom frontend (z.B. firstName, id) überhaupt zum Typ passen, es kann ja sein, dass ID und tenantID übereinstimmen, aber dass mir das frontend firstName anstatt companyName gesendet hat.
	//3. Ich würde den Polymorphismus gerne in SpringBoot einbinden so wie ich es bei CreateCustomerDTO habe und nicht mit IF ELSE arbeiten
	//4. Frontend soll mir nur die Daten senden, welches es auch ändern möchte, PATCH


	//DELETE
//	@DeleteMapping("/customers")
//	public ResponseEntity<ApiResponseDTO<CreateCustomerResponseDTO>> getCustomer(@Valid @RequestBody CreateCustomerRequestDTO request){
//	};

}
