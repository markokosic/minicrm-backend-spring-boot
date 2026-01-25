package com.markokosic.minicrm.modules.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class RegisterTenantResponseDTO {
    private Long tenantId;
    private String tenantName;
}
