package com.markokosic.minicrm.common.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.markokosic.minicrm.common.error.ApiErrorCode;
import com.markokosic.minicrm.exception.ValidationException;

import java.util.Iterator;
import java.util.Set;

public class JsonUtils {
	private JsonUtils() {
	}

	public static void validateAllowedFields(JsonNode json, Set<String> allowedFields) {
		Iterator<String> fieldNames = json.fieldNames();
		while (fieldNames.hasNext()) {
			String fieldName = fieldNames.next();
			if (!allowedFields.contains(fieldName)) {
				throw new ValidationException(ApiErrorCode.VALIDATION_INVALID_FIELD);

			}
		}
	}
}