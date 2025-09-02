package com.markokosic.minicrm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class RegisterTenantResponseDTO {
    private String tenantId;
    private String tenantName;
}
