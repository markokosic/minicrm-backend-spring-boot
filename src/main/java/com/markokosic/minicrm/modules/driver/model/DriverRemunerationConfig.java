package com.markokosic.minicrm.modules.driver.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name="driver_remuneration_configs")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "config_type")
public abstract class DriverRemunerationConfig {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "driver_id", nullable = false)
	private Driver driver;

	@Column(name = "tenant_id", nullable = false)
	private Long tenantId;

	@Column(name = "is_current_remuneration", nullable = false)
	private boolean current;

	@Column(name = "valid_from", nullable = false)
	private LocalDate validFrom;

	@Column(name = "valid_until")
	private LocalDate validUntil;

	public void activate(LocalDate from) {
		this.current = true;
		this.validFrom = from;
		this.validUntil = null;
	}

	public void deactivate(LocalDate until) {
		this.current = false;
		this.validUntil = until;
	}

}
