package com.markokosic.minicrm.repository;

import com.markokosic.minicrm.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {
	List<Company> findAllByTenantId(Long tenantId);
}
