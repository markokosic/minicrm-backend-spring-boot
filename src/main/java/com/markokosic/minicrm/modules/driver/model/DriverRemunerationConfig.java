package com.markokosic.minicrm.modules.driver.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="driver_remuneration_config")
public class DriverRemunerationConfig {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

//	@ManyToOne(fetch = FetchType.LAZY, optional = false)
//	@JoinColumn(name = "driver_id", nullable = false)
//	private Driver driver;

	@Column(name = "tenant_id", nullable = false)
	@NotNull
	private Long tenantId;

//	@Enumerated(EnumType.STRING)
//	@Column(name = "remuneration_model_type", nullable = false)
//	private RemunerationModelType remunerationModelType;

	@Column(name = "percentage", precision = 5, scale = 2)
	private BigDecimal percentage;

	@Column(name="daily_min_payout" )
	private Integer dailyMinPayout;

	@Column(name = "valid_from", nullable = false)
	private LocalDate validFrom;

	@Column(name = "valid_until")
	private LocalDate validUntil;


}
