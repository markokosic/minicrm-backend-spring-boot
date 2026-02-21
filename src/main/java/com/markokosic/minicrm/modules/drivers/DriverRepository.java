package com.markokosic.minicrm.modules.drivers;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {
	Page<Driver> findAllByTenantIdAndStatus(Long tenantId, DriverStatus status, Pageable pageable);
	Optional<Driver> findByIdAndTenantId(Long id, Long tenantId);
}
