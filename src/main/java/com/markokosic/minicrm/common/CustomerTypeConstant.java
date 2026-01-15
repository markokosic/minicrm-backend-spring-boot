package com.markokosic.minicrm.common;

public enum CustomerTypeConstant {
	BUSINESS(1L, "BUSINESS"),
	CONSUMER(2L, "CONSUMER");

	private final Long id;
	private final String code;

	CustomerTypeConstant(Long id, String code) {
		this.id = id;
		this.code = code;
	}

	// Getter hinzufügen
	public Long getId() { return id; }
	public String getCode() { return code; }
}