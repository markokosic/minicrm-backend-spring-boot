package com.markokosic.minicrm.dto.request;

import com.markokosic.minicrm.dto.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterTenantRequest extends UserResponse {
    private String tenantName;
    private String password;
}
