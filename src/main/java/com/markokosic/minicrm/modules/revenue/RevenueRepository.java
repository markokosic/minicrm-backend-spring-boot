package com.markokosic.minicrm.modules.revenue;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RevenueRepository extends JpaRepository<DailyRevenue, Long> {
}
