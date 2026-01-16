package com.markokosic.minicrm.service;

import com.markokosic.minicrm.common.ApiErrorCode;
import com.markokosic.minicrm.context.TenantContextHolder;
import com.markokosic.minicrm.dto.request.customer.CreateCustomerRequestDTO;
import com.markokosic.minicrm.dto.response.customer.CreateCustomerResponseDTO;
import com.markokosic.minicrm.exception.ValidationException;
import com.markokosic.minicrm.factory.CustomerFactory;
import com.markokosic.minicrm.mapper.CustomerMapper;
import com.markokosic.minicrm.model.customer.Customer;
import com.markokosic.minicrm.repository.CustomerRepository;
import com.markokosic.minicrm.repository.CustomerTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final CustomerMapper customerMapper;
	private final CustomerRepository customerRepository;
	private final CustomerTypeRepository customerTypeRepository;
	private final CustomerFactory customerFactory;

	@Transactional
	//needs to become orchestrator
	public CreateCustomerResponseDTO createCustomer(CreateCustomerRequestDTO request ) {
		try{
			Long tenantId = TenantContextHolder.getTenantId();
			// POLYMORPHISMUS: Factory erstellt die richtige Customer-Subklasse
			Customer customer = customerFactory.createCustomer(request, tenantId);
			customerRepository.save(customer);

			return customerMapper.toResponseDTO(customer);
		} catch (Exception e) {
			throw new ValidationException(ApiErrorCode.USER_NOT_FOUND, HttpStatus.CONFLICT);
		}
//		catch (Exception e) {
//			// log.error("Error creating customer", e);
//			throw new ValidationException(ApiErrorCode.CREATION_FAILED, HttpStatus.CONFLICT);
//		}
	}



}
