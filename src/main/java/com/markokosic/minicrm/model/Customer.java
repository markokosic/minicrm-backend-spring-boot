package com.markokosic.minicrm.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="customers")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;


	@Column(name = "tenant_id", nullable = false)
	@NotNull
	private Long tenantId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = "customer_type_id",
			referencedColumnName = "id",
			foreignKey = @ForeignKey(name = "FK_CUSTOMER_TYPE")
	)
//	@Column(name="customer_type_id")
	private Long customerTypeId;

	// === SEARCH & LIST FIELDS ===

	@Column(name="display_name", nullable = false)
	private String displayName;

	@Column(name="email")
	private String email;

	@Column(name="phone")
	private String phone;

	@Column(name="vat")
	private String vat;

	@Column(name="website")
	private String website;

	@Column(name="primary_country")
	private String primaryCountry;

	@Column(name="primary_city")
	private String primaryCity;

	@Column(name="status")
	private String status;


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
