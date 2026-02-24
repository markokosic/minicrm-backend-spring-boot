package com.markokosic.minicrm.modules.driver.repository;


import com.markokosic.minicrm.modules.driver.model.Driver;
import com.markokosic.minicrm.modules.driver.model.DriverStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {
	Page<Driver> findAllByTenantIdAndStatus(Long tenantId, DriverStatus status, Pageable pageable);
	Optional<Driver> findByIdAndTenantId(Long id, Long tenantId);
	Optional<List<Driver>> findAllByTenantIdAndIdIn(Long tenantId, List<Long> ids);
}
