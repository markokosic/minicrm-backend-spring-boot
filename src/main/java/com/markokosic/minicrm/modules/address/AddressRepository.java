package com.markokosic.minicrm.modules.address;

import com.markokosic.minicrm.modules.address.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
	List<Address> findAllByTenantIdAndCustomerId(Long tenantId, Long customerId);
}
