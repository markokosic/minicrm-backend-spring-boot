package com.markokosic.minicrm.auth.dto;

import com.markokosic.minicrm.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterTenantRequestDto extends UserDto {
    private String name;
}
