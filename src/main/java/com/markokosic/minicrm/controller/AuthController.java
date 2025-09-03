package com.markokosic.minicrm.controller;

import com.markokosic.minicrm.dto.request.LoginRequestDTO;
import com.markokosic.minicrm.dto.request.RegisterTenantRequestDTO;
import com.markokosic.minicrm.dto.response.ApiResponseDTO;
import com.markokosic.minicrm.dto.response.AuthResponseDTO;
import com.markokosic.minicrm.dto.response.RegisterTenantResponseDTO;
import com.markokosic.minicrm.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO<RegisterTenantResponseDTO>> register (@Valid @RequestBody RegisterTenantRequestDTO userAndTenantDto){
        RegisterTenantResponseDTO registrationResponse =  authService.registerNewTenant(userAndTenantDto);
        return ResponseEntity.ok(new ApiResponseDTO<>(true, registrationResponse, "Successfully registered new tenant."));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<ApiResponseDTO<AuthResponseDTO>> login(@Valid @RequestBody LoginRequestDTO loginRequest)  {
       AuthResponseDTO authResponse = authService.login(loginRequest);
        return ResponseEntity.ok(new ApiResponseDTO<>(true, authResponse, "Successfully logged in."));
    }

}
