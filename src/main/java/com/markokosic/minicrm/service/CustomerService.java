package com.markokosic.minicrm.service;

import com.markokosic.minicrm.common.ApiErrorCode;
import com.markokosic.minicrm.common.CustomerTypeConstant;
import com.markokosic.minicrm.context.TenantContextHolder;
import com.markokosic.minicrm.dto.request.CreateCustomerRequestDTO;
import com.markokosic.minicrm.dto.response.CreateCustomerResponseDTO;
import com.markokosic.minicrm.exception.ValidationException;
import com.markokosic.minicrm.mapper.CustomerMapper;
import com.markokosic.minicrm.model.Customer;
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

	@Transactional
	public CreateCustomerResponseDTO createCustomer(CreateCustomerRequestDTO request ) {
		try{

		boolean typeExists = customerTypeRepository.existsById(request.getCustomerTypeId());

			if (!typeExists) {
				//TODO ADD ERROR CODE
				throw new ValidationException(ApiErrorCode.VALIDATION_EMAIL_DUPLICATE, HttpStatus.BAD_REQUEST);
			}

			Long tenantId = TenantContextHolder.getTenantId();
			Customer customer = customerMapper.toEntity(request, tenantId);
			customerRepository.save(customer);

			if (CustomerTypeConstant.COMPANY.getId().equals(request.getCustomerTypeId())) {
//				createCompanyLogic(customer);
			} else if (CustomerTypeConstant.PERSON.getId().equals(request.getCustomerTypeId())) {
//				createPersonLogic(customer);
			}

			return customerMapper.toResponseDTO(customer);
		} catch (Exception e) {
			throw new ValidationException(ApiErrorCode.USER_NOT_FOUND, HttpStatus.CONFLICT);
		}
	}
}
