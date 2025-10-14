package com.markokosic.minicrm.service;


import com.markokosic.minicrm.common.ApiErrorCode;
import com.markokosic.minicrm.dto.request.LoginRequestDTO;
import com.markokosic.minicrm.dto.request.RegisterTenantRequestDTO;
import com.markokosic.minicrm.dto.response.AuthResponseDTO;
import com.markokosic.minicrm.dto.response.RegisterTenantResponseDTO;
import com.markokosic.minicrm.dto.response.UserResponseDTO;
import com.markokosic.minicrm.exception.ApiException;
import com.markokosic.minicrm.exception.AuthException;
import com.markokosic.minicrm.exception.ValidationException;
import com.markokosic.minicrm.model.Tenant;
import com.markokosic.minicrm.model.User;
import com.markokosic.minicrm.model.UserPrincipal;
import com.markokosic.minicrm.repository.TenantRepository;
import com.markokosic.minicrm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
//    private static final long ACCESS_TOKEN_EXPIRATION_IN_MINUTES = 30; //  30 Minutes
    private static final long ACCESS_TOKEN_EXPIRATION_IN_MINUTES = 1; //  1 Minute
    private static final long REFRESH_TOKEN_EXPIRATION_IN_MINUTES = 7 * 24 * 60L; // 7 Days in Minutes


    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
//    private final UserMapper userMapper;
    private final JWTService jwtService;
    private final    AuthenticationManager authenticationManager;

    @Transactional
    public RegisterTenantResponseDTO registerNewTenant(RegisterTenantRequestDTO userAndTenantDto) {
        try {
            Tenant savedTenant = createTenant(userAndTenantDto.getTenantName());
             createUser(userAndTenantDto, savedTenant);

            return new RegisterTenantResponseDTO(savedTenant.getId(), savedTenant.getName());
        } catch (Exception e) {
            log.error("DEBUG: Registration failed for tenant '" + userAndTenantDto.getTenantName() + "'");
            e.printStackTrace();
            throw new ApiException(ApiErrorCode.AUTH_REGISTRATION_FAILED);
        }
    }

    public Tenant createTenant (String name) {

        if(tenantRepository.existsByName(name)){
            throw new ValidationException(ApiErrorCode.TENANT_NAME_INVALID);
        }

        Tenant tenant = new Tenant();
        tenant.setName(name);
         return tenantRepository.save(tenant);
    }

    public void createUser (RegisterTenantRequestDTO request, Tenant tenant) {
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        if(optionalUser.isPresent()){
            throw new ValidationException(ApiErrorCode.VALIDATION_EMAIL_INVALID);
        }

        User newUser = new User();
        newUser.setTenantId(tenant.getId());
        newUser.setEmail(request.getEmail());
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(newUser);
    }




    public AuthResponseDTO login(LoginRequestDTO loginRequest) {
        Optional<User> optionalUser = userRepository.findByEmail(loginRequest.getEmail());

        if (optionalUser.isEmpty()) {
            throw new AuthException(ApiErrorCode.AUTH_INVALID_CREDENTIALS);
        }

        User user = optionalUser.get();

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            String accessToken = jwtService.generateToken(loginRequest.getEmail(), user.getTenantId(), ACCESS_TOKEN_EXPIRATION_IN_MINUTES);
            String refreshToken = jwtService.generateToken(loginRequest.getEmail(), user.getTenantId(), REFRESH_TOKEN_EXPIRATION_IN_MINUTES);
            UserResponseDTO userResponseDTO = new UserResponseDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());



            return new AuthResponseDTO(accessToken, refreshToken, userResponseDTO);

        } catch (AuthenticationException ex) {
            throw new AuthException(ApiErrorCode.AUTH_INVALID_CREDENTIALS);

        }
    }



    public UserResponseDTO getMe() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();


        UserPrincipal principal = (UserPrincipal) auth.getPrincipal(); // dein CustomUserDetails
        UserResponseDTO userDto = new UserResponseDTO();
        userDto.setId(principal.getId());
        userDto.setEmail(principal.getEmail());

        return userDto;
    }

    @GetMapping("/refresh-token")
    public void refreshAccessToken(String refreshToken){
        //return fehler wenn kein refreshToken im Cookie

        //validate ob refreshToken gültig ist
//        jwtService.validateToken(refreshToken);

        //vom JWTService .refreshAccessToken zurück geben mit neuem Tokenc

    }

}
