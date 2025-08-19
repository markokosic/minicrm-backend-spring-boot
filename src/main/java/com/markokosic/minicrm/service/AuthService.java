package com.markokosic.minicrm.service;

import com.markokosic.minicrm.dto.request.RegisterTenantRequestDto;
import com.markokosic.minicrm.dto.response.RegisterTenantResponseDto;
import com.markokosic.minicrm.entity.Tenant;
import com.markokosic.minicrm.repository.TenantRepository;
import com.markokosic.minicrm.entity.User;
import com.markokosic.minicrm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public RegisterTenantResponseDto registerNewTenant(RegisterTenantRequestDto userAndTenantDto) {
        System.out.println(userAndTenantDto);
        Tenant tenant = new Tenant();
        tenant.setName(userAndTenantDto.getTenantName());
        Tenant savedTenant = tenantRepository.save(tenant);

        User user = new User();
        user.setTenant(savedTenant);
        user.setPassword(userAndTenantDto.getPassword());
        user.setEmail(userAndTenantDto.getEmail());
        user.setFirstName(userAndTenantDto.getFirstName());
        user.setLastName(userAndTenantDto.getLastName());
        user.setPassword(passwordEncoder.encode(userAndTenantDto.getPassword()));
        User savedUser = userRepository.save(user);





        //JWT erstellen

        //User Klasse mappen auf RegisterTenantDtoResponse und returnen



        return null;
    }
}
