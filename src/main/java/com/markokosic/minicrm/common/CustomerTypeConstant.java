package com.markokosic.minicrm.common;

public enum CustomerTypeConstant {
	COMPANY(1L, "COMPANY"),
	PERSON(2L, "PERSON");

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