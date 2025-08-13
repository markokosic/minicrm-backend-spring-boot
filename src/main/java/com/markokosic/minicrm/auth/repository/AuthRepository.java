package com.markokosic.minicrm.auth.repository;

import com.markokosic.minicrm.auth.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository  extends JpaRepository<Tenant, Long> {
}
