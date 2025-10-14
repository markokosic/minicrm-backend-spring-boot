package com.markokosic.minicrm.controller;

import com.markokosic.minicrm.dto.request.LoginRequestDTO;
import com.markokosic.minicrm.dto.request.RegisterTenantRequestDTO;
import com.markokosic.minicrm.dto.response.*;
import com.markokosic.minicrm.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> getMe(){
        UserResponseDTO meResponse = authService.getMe();
        return ResponseEntity.ok(new ApiResponseDTO<>(true, meResponse, "Session is valid" ));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO<RegisterTenantResponseDTO>> register (@Valid @RequestBody RegisterTenantRequestDTO userAndTenantDto){
        RegisterTenantResponseDTO registrationResponse =  authService.registerNewTenant(userAndTenantDto);
        return ResponseEntity.ok(new ApiResponseDTO<>(true, registrationResponse, "Successfully registered new tenant."));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<AuthResponseDTO>> login(@Valid @RequestBody LoginRequestDTO loginRequest)  {
       AuthResponseDTO authResponse = authService.login(loginRequest);

        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", authResponse.getAccessToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .build();

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", authResponse.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(7 * 24 * 60 * 60)
                .build();


        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(new ApiResponseDTO<>(true, authResponse, "Successfully logged in"));
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<ApiResponseDTO<RefreshAccessTokenResponseDTO>> refreshAccessToken(@CookieValue("refreshToken") String refreshToken){


        String accessToken = authService.refreshAccessToken(refreshToken);

        //remove old accessToken cookie, return new accessToken cookie

        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", accessToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .build();

        RefreshAccessTokenResponseDTO responseDTO = new RefreshAccessTokenResponseDTO();
        responseDTO.setAccessToken(accessToken);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .body(new ApiResponseDTO<>(true, responseDTO, "Successfully refreshed Access Token"));

    }
}
