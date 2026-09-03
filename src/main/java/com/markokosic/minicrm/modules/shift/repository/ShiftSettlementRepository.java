package com.markokosic.minicrm.modules.shift.repository;

import com.markokosic.minicrm.modules.shift.model.ShiftSettlement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShiftSettlementRepository extends JpaRepository<ShiftSettlement, Long> {

	Optional<ShiftSettlement> findByShiftId(Long shiftId);
}
