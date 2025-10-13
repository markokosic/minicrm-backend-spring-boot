package com.markokosic.minicrm.dto.request;

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
    @Size(min = 3, message = "min-size-3")
    @Size(max = 15, message = "max-size-15")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "tenant-name-invalid")
    private String tenantName;

    @NotBlank
    @Size(min = 8, message = "min-size-8")
    @Size(max = 100, message = "max-size-100")
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;
}
