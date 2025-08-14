package com.markokosic.minicrm.auth.controller;

import com.markokosic.minicrm.auth.dto.request.RegisterTenantRequestDto;
import com.markokosic.minicrm.auth.dto.response.RegisterTenantResponseDto;
import com.markokosic.minicrm.auth.service.AuthService;
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
    public RegisterTenantResponseDto register (@RequestBody RegisterTenantRequestDto userAndTenantDto){
        return authService.registerNewTenant(userAndTenantDto);
    }

}
