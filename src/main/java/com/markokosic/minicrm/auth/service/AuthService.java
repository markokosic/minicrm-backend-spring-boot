package com.markokosic.minicrm.auth.service;

import com.markokosic.minicrm.auth.dto.RegisterTenantRequestDto;
import com.markokosic.minicrm.auth.dto.RegisterTenantResponseDto;
import com.markokosic.minicrm.auth.entity.Tenant;

public interface AuthService {
    RegisterTenantResponseDto register(RegisterTenantRequestDto tenantDto);

}
