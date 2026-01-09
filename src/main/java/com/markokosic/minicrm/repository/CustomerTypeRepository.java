package com.markokosic.minicrm.repository;

import com.markokosic.minicrm.model.CustomerType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerTypeRepository extends JpaRepository<CustomerType, Long> {
}
