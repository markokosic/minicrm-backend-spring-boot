package com.markokosic.minicrm.controller;

import com.markokosic.minicrm.dto.request.LoginRequest;
import com.markokosic.minicrm.dto.request.RegisterTenantRequest;
import com.markokosic.minicrm.dto.response.ApiResponse;
import com.markokosic.minicrm.dto.response.AuthResponse;
import com.markokosic.minicrm.dto.response.RegisterTenantResponse;
import com.markokosic.minicrm.service.AuthService;
import com.markokosic.minicrm.service.UserService;
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
    private final UserService userService;

    @PostMapping("/register")
    public RegisterTenantResponse register (@Valid @RequestBody RegisterTenantRequest userAndTenantDto){
        return authService.registerNewTenant(userAndTenantDto);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest user){
       AuthResponse authResponse = userService.verify(user);
        return ResponseEntity.ok(new ApiResponse<>(true, authResponse, "Successfully logged in."));
    }

}
