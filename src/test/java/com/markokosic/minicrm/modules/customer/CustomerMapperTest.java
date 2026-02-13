package com.markokosic.minicrm.modules.customer;

import com.markokosic.minicrm.modules.customer.dto.request.*;
import com.markokosic.minicrm.modules.customer.dto.response.*;
import com.markokosic.minicrm.modules.customer.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {

	private CustomerMapper mapper;

	@BeforeEach
	void setUp() {
		mapper = new CustomerMapperImpl();
	}

	@Test
	@DisplayName("map ConsumerCustomer to ConsumerCustomerResponseDTO")
	void shouldMapConsumerToDto() {
		// Given
		ConsumerCustomer consumer = new ConsumerCustomer();
		consumer.setId(100L);
		consumer.setFirstName("Max");
		consumer.setLastName("Mustermann");
		consumer.setEmail("max@mustermann.com");
		consumer.setPhone("+012345");

		// When
		CustomerResponseDTO result = mapper.toDto(consumer);

		// Then
		assertInstanceOf(ConsumerCustomerResponseDTO.class, result);
		ConsumerCustomerResponseDTO dto = (ConsumerCustomerResponseDTO) result;
		assertEquals("Max", dto.getFirstName());
		assertEquals(100L, dto.getId());
	}

	@Test
	@DisplayName("map ConsumerCustomer to Entity")
	void shouldMapCreateDtoToEntityWithTenantId() {
		// Given
		CreateConsumerCustomerRequestDTO dto = new CreateConsumerCustomerRequestDTO();
		dto.setFirstName("Erika");
		Long tenantId = 5L;

		// When
		Customer entity = mapper.toEntity(dto, tenantId);

		// Then
		assertNotNull(entity);
		assertTrue(entity instanceof ConsumerCustomer);
		assertEquals(tenantId, entity.getTenantId());
		assertEquals("Erika", ((ConsumerCustomer) entity).getFirstName());
		assertNull(entity.getId());
		assertNull(entity.getCreatedAt());
	}

	@Test
	@DisplayName("should only update partially")
	void shouldUpdateBusinessCustomerIgnoringNulls() {
		// Given
		BusinessCustomer existingCustomer = new BusinessCustomer();
		existingCustomer.setCompanyName("Alte Firma");
		existingCustomer.setVat("DE123");

		UpdateBusinessCustomerRequestDTO updateDto = new UpdateBusinessCustomerRequestDTO();
		updateDto.setCompanyName("Neue Firma");
		updateDto.setVat(null);

		// When
		mapper.updateBusinessCustomerFromDTO(updateDto, existingCustomer);

		// Then
		assertEquals("Neue Firma", existingCustomer.getCompanyName());
		assertEquals("DE123", existingCustomer.getVat());
	}
}