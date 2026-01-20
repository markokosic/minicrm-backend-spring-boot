package com.markokosic.minicrm.modules.customer;

import com.markokosic.minicrm.common.error.ApiErrorCode;
import com.markokosic.minicrm.exception.NotFoundException;
import com.markokosic.minicrm.modules.customer.dto.request.CreateCustomerRequestDTO;
import com.markokosic.minicrm.modules.customer.dto.request.UpdateBusinessCustomerRequestDTO;
import com.markokosic.minicrm.modules.customer.dto.request.UpdateConsumerCustomerRequestDTO;
import com.markokosic.minicrm.modules.customer.dto.request.UpdateCustomerRequestDTO;
import com.markokosic.minicrm.modules.customer.dto.response.CustomerResponseDTO;
import com.markokosic.minicrm.modules.customer.model.BusinessCustomer;
import com.markokosic.minicrm.modules.customer.model.ConsumerCustomer;
import com.markokosic.minicrm.modules.customer.model.Customer;
import com.markokosic.minicrm.modules.tenant.TenantContextHolder;
import lombok.RequiredArgsConstructor;
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
	public CustomerResponseDTO createCustomer(CreateCustomerRequestDTO request ) {
		Long tenantId = TenantContextHolder.getTenantId();
		Customer customer = customerFactory.createCustomer(request, tenantId);
		customerRepository.save(customer);
			return customerMapper.toDto(customer);

	}

	public  List<CustomerResponseDTO> getCustomers( ) {
		Long tenantId = TenantContextHolder.getTenantId();
		return  customerRepository.findAllByTenantId(tenantId).stream().map(customerMapper::toDto).toList();
	}

	@Transactional
	public CustomerResponseDTO updateCustomer(UpdateCustomerRequestDTO request) {
		Long tenantId = TenantContextHolder.getTenantId();

		Customer customer = customerRepository.findByIdAndTenantId(request.getId(), tenantId)
				.orElseThrow(() -> new NotFoundException(ApiErrorCode.CUSTOMER_NOT_FOUND));


		if (customer instanceof BusinessCustomer ) {
			customerMapper.updateBusinessCustomerFromDTO(
					(UpdateBusinessCustomerRequestDTO) request,
							(BusinessCustomer) customer
			);
		} else if ( customer instanceof ConsumerCustomer) {
			customerMapper.updateConsumerCustomerFromDTO(
					(UpdateConsumerCustomerRequestDTO) request,
							(ConsumerCustomer) customer
			);
		}


		customerRepository.save(customer);
		return customerMapper.toDto(customer);

	}



}
