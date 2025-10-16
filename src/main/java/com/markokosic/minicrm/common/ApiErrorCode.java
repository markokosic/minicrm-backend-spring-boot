package com.markokosic.minicrm.common;

public enum ApiErrorCode {
	// Authentication Errors
	AUTH_INVALID_CREDENTIALS("auth-invalid-credentials", "The email or password you entered is incorrect"),
	AUTH_TOKEN_EXPIRED("auth-token-expired", "Token has expired"),
	AUTH_REGISTRATION_FAILED("auth-registration-failed", "Registration failed"),
	AUTH_NO_TOKEN_RECEIVED("no-token-received", "No token received, please login again."),

	// Validation Errors
	VALIDATION_NOT_BLANK("not-blank", "Field cannot be blank"),
	VALIDATION_EMAIL_REQUIRED("email-required", "Email is required"),
	VALIDATION_EMAIL_INVALID("email-invalid", "Email is invalid"),
	VALIDATION_PASSWORD_REQUIRED("password-required", "Password is required"),
	VALIDATION_PASSWORD_TOO_SHORT("password-too-short", "Password must be at least 8 characters"),
	VALIDATION_MIN_SIZE_3("min-size-3", "Must be at least 3 characters"),
	VALIDATION_MIN_SIZE_8("min-size-8", "Must be at least 8 characters"),
	VALIDATION_MAX_SIZE_15("max-size-15", "Must be at most 15 characters"),
	VALIDATION_MAX_SIZE_100("max-size-100", "Must be at most 100 characters"),

	// Tenant / Business Errors
	TENANT_NAME_INVALID("tenant-name-invalid", "Tenant name is invalid"),

	//NOT FOUND EXCEPTIONS
	USER_NOT_FOUND("user-not-found", "User not found");

	private final String key;
	private final String message;

	ApiErrorCode(String key, String message) {
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
