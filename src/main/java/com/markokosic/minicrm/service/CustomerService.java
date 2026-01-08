package com.markokosic.minicrm.service;

import com.markokosic.minicrm.common.ApiErrorCode;
import com.markokosic.minicrm.context.TenantContextHolder;
import com.markokosic.minicrm.dto.request.CreateCustomerRequestDTO;
import com.markokosic.minicrm.dto.response.CustomerResponseDTO;
import com.markokosic.minicrm.exception.ValidationException;
import com.markokosic.minicrm.mapper.CustomerMapper;
import com.markokosic.minicrm.model.Customer;
import com.markokosic.minicrm.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final CustomerMapper customerMapper;
	private final CustomerRepository customerRepository;

	public CustomerResponseDTO createCustomer(CreateCustomerRequestDTO request ) {
		try{
			Long tenantId = TenantContextHolder.getTenantId();
			Customer customer = customerMapper.toEntity(request, tenantId);
			customerRepository.save(customer);

			return customerMapper.toResponseDTO(customer);
		} catch (Exception e) {
			throw new ValidationException(ApiErrorCode.USER_NOT_FOUND, HttpStatus.CONFLICT);
		}
	}
}
