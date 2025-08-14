package com.markokosic.minicrm.auth.dto.response;

public class RegisterTenantResponseDto {
    private String message;

    public RegisterTenantResponseDto() {}

    public RegisterTenantResponseDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
