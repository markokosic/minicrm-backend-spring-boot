package com.markokosic.minicrm.dto.response;

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
