package com.markokosic.minicrm.auth.dto.response;

import com.markokosic.minicrm.user.dto.UserDto;

public class AuthResponseDto {
    private String accessToken;
    private String refreshToken;
    private UserDto user;
}
