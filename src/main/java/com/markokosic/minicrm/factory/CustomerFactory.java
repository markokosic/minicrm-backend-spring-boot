package com.markokosic.minicrm.factory;

import com.markokosic.minicrm.common.CustomerType;
import com.markokosic.minicrm.dto.request.customer.CreateBusinessCustomerRequestDTO;
import com.markokosic.minicrm.dto.request.customer.CreateConsumerCustomerRequestDTO;
import com.markokosic.minicrm.dto.request.customer.CreateCustomerRequestDTO;
import com.markokosic.minicrm.model.customer.BusinessCustomer;
import com.markokosic.minicrm.model.customer.ConsumerCustomer;
import com.markokosic.minicrm.model.customer.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerFactory {

	public Customer createCustomer(CreateCustomerRequestDTO request, Long tenantId) {
		CustomerType type = request.getType();

		if (type == null) {
			throw new IllegalArgumentException("Customer type cannot be null");
		}

		return switch (type) {
			case CustomerType.BUSINESS -> createBusinessCustomer((CreateBusinessCustomerRequestDTO) request, tenantId);
			case CustomerType.CONSUMER -> createConsumerCustomer((CreateConsumerCustomerRequestDTO) request, tenantId);
		};
	}

	private BusinessCustomer createBusinessCustomer(CreateBusinessCustomerRequestDTO request, Long tenantId) {
		BusinessCustomer customer = new BusinessCustomer();
		customer.setTenantId(tenantId);
		customer.setCompanyName(request.getCompanyName());
		customer.setVat(request.getVat());
		customer.setEmail(request.getEmail());
		customer.setPhone(request.getPhone());
		customer.setWebsite(request.getWebsite());
		return customer;
	}

	private ConsumerCustomer createConsumerCustomer(CreateConsumerCustomerRequestDTO request, Long tenantId) {
		ConsumerCustomer customer = new ConsumerCustomer();
		customer.setTenantId(tenantId);
		customer.setFirstName(request.getFirstName());
		customer.setLastName(request.getLastName());
		customer.setEmail(request.getEmail());
		customer.setPhone(request.getPhone());
		return customer;
	}
}