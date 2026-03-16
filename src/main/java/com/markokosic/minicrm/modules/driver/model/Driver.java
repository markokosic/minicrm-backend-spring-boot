package com.markokosic.minicrm.modules.driver.model;

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
@Table(name="drivers")
public class Driver {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "tenant_id", nullable = false)
	@NotNull
	private Long tenantId;

	@Column(name="first_name")
	private String firstName;

	@Column(name="last_name")
	private String lastName;

	@Column(name="email")
	private String email;

	@Column(name="phone")
	private String phone;

//	@NotNull
//	@Column(name="remuneration_model", nullable = false)
//	@Enumerated(EnumType.STRING)
//	private RemunerationModelType remunerationModelType;
//
//	@OneToMany(
//			mappedBy = "driver",
//			cascade = CascadeType.ALL,
//			orphanRemoval = true
//	)
//	private List<DriverRemunerationConfig> remunerationConfigs = new ArrayList<>();

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Column(name="status", nullable = false)
	@Enumerated(EnumType.STRING)
	private DriverStatus status;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
		this.status = DriverStatus.ACTIVE;
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

}
