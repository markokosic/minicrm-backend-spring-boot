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
