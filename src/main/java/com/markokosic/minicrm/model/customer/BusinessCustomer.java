package com.markokosic.minicrm.model.customer;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;

@Entity
@DiscriminatorValue("BUSINESS")
public class BusinessCustomer extends Customer{

	@Column(name="name")
	private String name;

	@Column(name="vat")
	private String vat;

	@Email
	@Column(name="email")
	private String email;

	@Column(name="phone")
	private String phone;

	@Column(name="website")
	private String website;

}
