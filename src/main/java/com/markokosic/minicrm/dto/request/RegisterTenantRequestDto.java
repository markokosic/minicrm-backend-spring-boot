package com.markokosic.minicrm.dto.request;

import com.markokosic.minicrm.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterTenantRequestDto extends UserDto {
    private String tenantName;
    private String password;
}
