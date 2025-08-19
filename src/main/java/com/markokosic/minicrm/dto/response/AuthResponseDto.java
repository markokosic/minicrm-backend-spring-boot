package com.markokosic.minicrm.dto.response;

import com.markokosic.minicrm.dto.UserDto;

public class AuthResponseDto {
    private String accessToken;
    private String refreshToken;
    private UserDto user;
}
