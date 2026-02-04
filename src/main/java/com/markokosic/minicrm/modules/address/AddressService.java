package com.markokosic.minicrm.modules.address;

import com.markokosic.minicrm.modules.address.dto.request.CreateAddressRequestDTO;
import com.markokosic.minicrm.modules.address.dto.response.AddressResponseDTO;
import com.markokosic.minicrm.modules.address.model.Address;
import com.markokosic.minicrm.modules.customer.CustomerTenantValidator;
import com.markokosic.minicrm.modules.customer.model.Customer;
import com.markokosic.minicrm.modules.tenant.TenantService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
	private final CustomerTenantValidator customerTenantValidator;
	private final AddressMapper addressMapper;
	private final AddressRepository addressRepository;
	private final TenantService tenantService;

	@Transactional
	public AddressResponseDTO createAddress(Long customerId , CreateAddressRequestDTO dto) {
		Customer customer = customerTenantValidator.getCustomerByTenantAndIdOrThrow(customerId);
		Long tenantId = tenantService.getTenantIdFromContextHolder();
		Address address = addressMapper.toEntity(dto,tenantId, customer);
		Address saved = addressRepository.save(address);
		return addressMapper.toDto(saved);
	}

	public List<AddressResponseDTO> getAddressesByCustomer(Long customerId){
		 customerTenantValidator.getCustomerByTenantAndIdOrThrow(customerId);
		Long tenantId = tenantService.getTenantIdFromContextHolder();
		return addressRepository.findAllByTenantIdAndCustomerId(tenantId, customerId)
				.stream()
				.map(addressMapper::toDto)
				.toList();
	}



}