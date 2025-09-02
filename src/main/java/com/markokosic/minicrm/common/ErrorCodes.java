package com.markokosic.minicrm.common;

public class ErrorCodes {

	public static final String NOT_BLANK = "not-blank";
	public static final String NOT_BLANK_MESSAGE = "Field cannot be blank";

	public static final String EMAIL_REQUIRED = "email-required";
	public static final String EMAIL_REQUIRED_MESSAGE = "Email is required";

	public static final String EMAIL_INVALID = "email-invalid";
	public static final String EMAIL_INVALID_MESSAGE = "Email is invalid";

	public static final String PASSWORD_REQUIRED = "password-required";
	public static final String PASSWORD_REQUIRED_MESSAGE = "Password is required";

	public static final String PASSWORD_TOO_SHORT = "password-too-short";
	public static final String PASSWORD_TOO_SHORT_MESSAGE = "Password must be at least 8 characters";

	public static final String AUTH_INVALID_CREDENTIALS = "auth-invalid-credentials";
	public static final String AUTH_INVALID_CREDENTIALS_MESSAGE = "Invalid login credentials";

	public static final String MIN_SIZE_3 = "min-size-3";
	public static final String MIN_SIZE_3_MESSAGE = "Must be at least 3 characters";

	public static final String MIN_SIZE_8 = "min-size-8";
	public static final String MIN_SIZE_8_MESSAGE = "Must be at least 8 characters";

	public static final String MAX_SIZE_15 = "max-size-15";
	public static final String MAX_SIZE_15_MESSAGE = "Must be at most 15 characters";

	public static final String MAX_SIZE_100 = "max-size-100";
	public static final String MAX_SIZE_100_MESSAGE = "Must be at most 100 characters";

	public static final String TENANTNAME_INVALID = "tenantname-invalid";
	public static final String TENANTNAME_INVALID_MESSAGE = "Tenant name is invalid";

}
