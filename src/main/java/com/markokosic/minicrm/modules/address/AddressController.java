package com.markokosic.minicrm.modules.address;

import com.markokosic.minicrm.common.dto.response.ApiResponseDTO;
import com.markokosic.minicrm.modules.address.dto.request.CreateAddressRequestDTO;
import com.markokosic.minicrm.modules.address.dto.response.AddressResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers/{customerId}/addresses")
@Slf4j
@RequiredArgsConstructor
public class AddressController {

	private final AddressService  addressService;

	@PostMapping
	public ResponseEntity<ApiResponseDTO<AddressResponseDTO>> createAddress(@PathVariable Long customerId, @Valid @RequestBody CreateAddressRequestDTO request) {
		AddressResponseDTO address = addressService.createAddress(customerId, request);
		return ResponseEntity.ok(new ApiResponseDTO<>(true, address, "Successfully added address to customer"));
	}


	@GetMapping
	public ResponseEntity<ApiResponseDTO<List<AddressResponseDTO>>> getAddressesByCustomer(@PathVariable Long customerId) {
		List<AddressResponseDTO> address = addressService.getAddressesByCustomer(customerId);
		return ResponseEntity.ok(new ApiResponseDTO<>(true, address, "Successfully fetched addresses from customer"));
	}


//	@GetMapping("/{aId}")
//	public ResponseEntity<ApiResponseDTO<AddressResponseDTO>> getAddressById(@PathVariable Long cId, @PathVariable Long aId) {
//		List<AddressResponseDTO> address = addressService.getAddressById(cId, aId);
//		return ResponseEntity.ok(new ApiResponseDTO<>(true, address, "Successfully added address to customer"));
//	}
//
//	@PatchMapping("/{aid}")
//	public ResponseEntity<ApiResponseDTO<AddressResponseDTO>> getAddressById(@PathVariable Long cId, @PathVariable Long aId) {
//		List<AddressResponseDTO> address = addressService.getAddressById(cId, aId);
//		return ResponseEntity.ok(new ApiResponseDTO<>(true, address, "Successfully added address to customer"));
//	}

}
