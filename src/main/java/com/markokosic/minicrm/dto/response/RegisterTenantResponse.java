package com.markokosic.minicrm.dto.response;

public class RegisterTenantResponse {
    private String message;

    public RegisterTenantResponse() {}

    public RegisterTenantResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
