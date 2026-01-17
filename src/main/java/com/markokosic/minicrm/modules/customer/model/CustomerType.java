package com.markokosic.minicrm.modules.customer.model;

public enum CustomerType {
	BUSINESS(1L, "BUSINESS"),
	CONSUMER(2L, "CONSUMER");

	private final Long id;
	private final String code;

	CustomerType(Long id, String code) {
		this.id = id;
		this.code = code;
	}

	public Long getId() { return id; }
	public String getCode() { return code; }
}