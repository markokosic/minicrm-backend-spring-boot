package com.markokosic.minicrm.repository;

import com.markokosic.minicrm.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
