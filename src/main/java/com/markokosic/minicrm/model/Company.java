package com.markokosic.minicrm.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.TenantId;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="companies")
public class Company {

	@Id
	@Column(name="id")
	private Long id;

	@TenantId
	@Column(name="tenant_id")
	private Long tenant_id;

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

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}



}
