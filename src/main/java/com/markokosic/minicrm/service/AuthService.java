package com.markokosic.minicrm.service;


import com.markokosic.minicrm.dto.request.LoginRequestDTO;
import com.markokosic.minicrm.dto.request.RegisterTenantRequestDTO;
import com.markokosic.minicrm.dto.response.AuthResponseDTO;
import com.markokosic.minicrm.dto.response.RegisterTenantResponseDTO;
import com.markokosic.minicrm.dto.response.UserResponseDTO;
import com.markokosic.minicrm.exception.BadCredentialsException;
import com.markokosic.minicrm.mapper.UserMapper;
import com.markokosic.minicrm.model.Tenant;
import com.markokosic.minicrm.repository.TenantRepository;
import com.markokosic.minicrm.model.User;
import com.markokosic.minicrm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Autowired
    JWTService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Transactional
    public RegisterTenantResponseDTO registerNewTenant(RegisterTenantRequestDTO userAndTenantDto) {
       try{


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


        return new RegisterTenantResponseDTO(savedTenant.getId(), savedTenant.getName());

       } catch (AuthenticationException ex){
           throw new BadCredentialsException();
       }
    }


    public AuthResponseDTO login(LoginRequestDTO loginRequest) {
        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            User userData = userRepository.findByEmail(loginRequest.getEmail());

            String accessToken = jwtService.generateToken(loginRequest.getEmail(), userData.getTenantId());
            UserResponseDTO userResponseDTO = new UserResponseDTO(userData.getFirstName(), userData.getLastName(), userData.getEmail());
            return new AuthResponseDTO(accessToken, userResponseDTO);

        } catch (AuthenticationException ex) {
            throw new BadCredentialsException();
        }
    }
}
