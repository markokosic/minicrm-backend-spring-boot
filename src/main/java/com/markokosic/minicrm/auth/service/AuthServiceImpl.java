package com.markokosic.minicrm.auth.service;

import com.markokosic.minicrm.auth.dto.RegisterTenantRequestDto;
import com.markokosic.minicrm.auth.dto.RegisterTenantResponseDto;
import com.markokosic.minicrm.auth.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;

    @Override
    public RegisterTenantResponseDto register(RegisterTenantRequestDto tenantDto) {
        //Repo aufrufen

        //In Repo den den Tenant erstellen

        //In Repo den SA User erstellen

        // Password encrypten

        //JWT erstellen

        //User Klasse mappen auf RegisterTenantDtoResponse und returnen


        return null;
    }
}
