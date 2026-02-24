package com.markokosic.minicrm.modules.revenue;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RevenueMapper {
	DailyRevenue toEntity(CreateDailyRevenueRequestDTO dto, Long tenantId);
}
