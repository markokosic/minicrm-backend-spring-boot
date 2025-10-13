package com.markokosic.minicrm.common;

public enum ErrorCode {
	//AUTHENTICATION
	AUTH_BAD_CREDENTIALS("auth-invalid-credentials", "The email or password you entered is incorrect"),


	NOT_BLANK("not-blank", "Field cannot be blank"),
	EMAIL_REQUIRED("email-required", "Email is required"),
	EMAIL_INVALID("email-invalid", "Email is invalid"),
	PASSWORD_REQUIRED("password-required", "Password is required"),
	PASSWORD_TOO_SHORT("password-too-short", "Password must be at least 8 characters"),
	AUTH_INVALID_CREDENTIALS("auth-invalid-credentials", "Invalid login credentials"),
	MIN_SIZE_3("min-size-3", "Must be at least 3 characters"),
	MIN_SIZE_8("min-size-8", "Must be at least 8 characters"),
	MAX_SIZE_15("max-size-15", "Must be at most 15 characters"),
	MAX_SIZE_100("max-size-100", "Must be at most 100 characters"),
	TENANTNAME_INVALID("tenantname-invalid", "Tenant name is invalid");



	private final String key;
	private final String message;

	ErrorCode(String key, String message) {
		this.key = key;
		this.message = message;
	}

	public String getKey() {
		return key;
	}

	public String getMessage() {
		return message;
	}
}
