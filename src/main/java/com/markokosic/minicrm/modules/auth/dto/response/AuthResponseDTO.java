package com.markokosic.minicrm.modules.auth.dto.response;

import com.markokosic.minicrm.modules.user.dto.response.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {
    private String accessToken;
    private String refreshToken;
    private UserResponseDTO user;
}
