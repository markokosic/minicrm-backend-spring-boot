package com.markokosic.minicrm.modules.customer;

import com.markokosic.minicrm.common.error.ApiErrorCode;
import com.markokosic.minicrm.modules.tenant.TenantContextHolder;
import com.markokosic.minicrm.modules.customer.dto.request.CreateCustomerRequestDTO;
import com.markokosic.minicrm.modules.customer.dto.response.CustomerResponseDTO;
import com.markokosic.minicrm.exception.ValidationException;
import com.markokosic.minicrm.modules.customer.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final CustomerMapper customerMapper;
	private final CustomerRepository customerRepository;
	private final CustomerFactory customerFactory;

	@Transactional
	//needs to become orchestrator
	public CustomerResponseDTO createCustomer(CreateCustomerRequestDTO request ) {
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

	public  List<CustomerResponseDTO> getCustomers( ) {
		Long tenantId = TenantContextHolder.getTenantId();
		return  customerRepository.findAllByTenantId(tenantId).stream().map(customerMapper::toResponseDTO).toList();
	}



}
