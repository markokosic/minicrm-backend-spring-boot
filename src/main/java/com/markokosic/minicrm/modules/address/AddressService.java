package com.markokosic.minicrm.modules.address;

import com.markokosic.minicrm.modules.address.dto.request.CreateAddressRequestDTO;
import com.markokosic.minicrm.modules.address.dto.response.AddressResponseDTO;
import com.markokosic.minicrm.modules.address.model.Address;
import com.markokosic.minicrm.modules.customer.CustomerTenantValidator;
import com.markokosic.minicrm.modules.customer.model.Customer;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {
	private final CustomerTenantValidator customerTenantValidator;
	private final AddressMapper addressMapper;
	private final AddressRepository addressRepository;

	@Transactional
	public AddressResponseDTO addAddressToCustomer(Long customerId , CreateAddressRequestDTO dto) {
		Customer customer = customerTenantValidator.getCustomerByTenantAndIdOrThrow(customerId);
		Address address = addressMapper.toEntity(dto, customer);
		Address saved = addressRepository.save(address);
		return addressMapper.toDto(saved);

	}



}