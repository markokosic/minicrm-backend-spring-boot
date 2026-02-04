package com.markokosic.minicrm.modules.address;

import com.markokosic.minicrm.common.dto.response.ApiResponseDTO;
import com.markokosic.minicrm.modules.address.dto.request.CreateAddressRequestDTO;
import com.markokosic.minicrm.modules.address.dto.response.AddressResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers/{customerId}/addresses")
@Slf4j
@RequiredArgsConstructor
public class AddressController {

	private final AddressService  addressService;


	@PostMapping
	public ResponseEntity<ApiResponseDTO<AddressResponseDTO>>  addAddressToCustomer(@PathVariable Long customerId, @Valid @RequestBody CreateAddressRequestDTO request) {
		AddressResponseDTO address = addressService.addAddressToCustomer(customerId, request);
		return ResponseEntity.ok(new ApiResponseDTO<>(true, address, "Successfully added address to customer"));
	}
}
