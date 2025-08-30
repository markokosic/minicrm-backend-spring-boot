package com.markokosic.minicrm.service;


import com.markokosic.minicrm.dto.request.RegisterTenantRequest;
import com.markokosic.minicrm.dto.response.RegisterTenantResponse;
import com.markokosic.minicrm.model.Tenant;
import com.markokosic.minicrm.repository.TenantRepository;
import com.markokosic.minicrm.model.User;
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
    public RegisterTenantResponse registerNewTenant(RegisterTenantRequest userAndTenantDto) {
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
