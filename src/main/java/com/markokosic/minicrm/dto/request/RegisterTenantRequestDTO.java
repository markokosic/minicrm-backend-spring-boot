package com.markokosic.minicrm.dto.request;

import com.markokosic.minicrm.common.ErrorCodes;
import com.markokosic.minicrm.dto.response.UserResponseDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterTenantRequestDTO  {

    @NotBlank
    @Size(min = 3, message = ErrorCodes.MIN_SIZE_3)
    @Size(max = 15, message = ErrorCodes.MAX_SIZE_15)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = ErrorCodes.TENANTNAME_INVALID)
    private String tenantName;

    @NotBlank
    @Size(min = 8, message = ErrorCodes.MIN_SIZE_8)
    @Size(max = 100, message = ErrorCodes.MAX_SIZE_100)
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;
}
