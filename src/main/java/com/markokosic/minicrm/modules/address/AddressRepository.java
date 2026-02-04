package com.markokosic.minicrm.modules.address;

import com.markokosic.minicrm.modules.address.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
