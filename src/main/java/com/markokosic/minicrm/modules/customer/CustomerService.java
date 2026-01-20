package com.markokosic.minicrm.modules.customer;

import com.markokosic.minicrm.common.error.ApiErrorCode;
import com.markokosic.minicrm.exception.NotFoundException;
import com.markokosic.minicrm.exception.ValidationException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.markokosic.minicrm.modules.customer.dto.request.CreateCustomerRequestDTO;
import com.markokosic.minicrm.modules.customer.dto.request.UpdateBusinessCustomerRequestDTO;
import com.markokosic.minicrm.modules.customer.dto.request.UpdateConsumerCustomerRequestDTO;
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
	private final ObjectMapper objectMapper;

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
	public CustomerResponseDTO updateCustomer(Long id, JsonNode requestBody) {
		Long tenantId = TenantContextHolder.getTenantId();

		Customer customer = customerRepository.findByIdAndTenantId(id, tenantId)
				.orElseThrow(() -> new NotFoundException(ApiErrorCode.CUSTOMER_NOT_FOUND));

		if (customer instanceof BusinessCustomer) {
			validateAllowedFieldsForBusinessCustomer(requestBody);
			UpdateBusinessCustomerRequestDTO dto =
					objectMapper.convertValue(requestBody, UpdateBusinessCustomerRequestDTO.class);
			customerMapper.updateBusinessCustomerFromDTO(dto, (BusinessCustomer) customer);
		} else if (customer instanceof ConsumerCustomer) {
			validateAllowedFieldsForConsumerCustomer(requestBody);
			UpdateConsumerCustomerRequestDTO dto =
					objectMapper.convertValue(requestBody, UpdateConsumerCustomerRequestDTO.class);
			customerMapper.updateConsumerCustomerFromDTO(dto, (ConsumerCustomer) customer);
		}

		customerRepository.save(customer);
		return customerMapper.toDto(customer);

	}

	private void validateAllowedFieldsForBusinessCustomer(JsonNode requestBody) {
		var allowedFields = java.util.Set.of("companyName", "vat", "email", "phone", "website");
		validateAllowedFields(requestBody, allowedFields);
	}

	private void validateAllowedFieldsForConsumerCustomer(JsonNode requestBody) {
		var allowedFields = java.util.Set.of("firstName", "lastName", "email", "phone");
		validateAllowedFields(requestBody, allowedFields);
	}

	private void validateAllowedFields(JsonNode requestBody, java.util.Set<String> allowedFields) {
		var fieldNames = requestBody.fieldNames();
		while (fieldNames.hasNext()) {
			String fieldName = fieldNames.next();
			if (!allowedFields.contains(fieldName)) {
				throw new ValidationException(ApiErrorCode.VALIDATION_INVALID_FIELD);
			}
		}
	}



}
