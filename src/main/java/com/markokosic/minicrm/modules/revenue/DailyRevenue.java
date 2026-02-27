package com.markokosic.minicrm.modules.revenue;

import com.markokosic.minicrm.modules.driver.model.Driver;
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
@Table(name="daily_revenue")
public class DailyRevenue {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "tenant_id", nullable = false)
	@NotNull
	private Long tenantId;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "driver_id", nullable = false)
	private Driver driver;

	@Column(name="licence_plate", nullable = false)
	private String licencePlate;

	@Column(name="date", nullable = false)
	private LocalDate date;

	@Column(name="kilometers_driven", nullable = false)
	private BigDecimal kilometersDriven;

	@Column(name="revenue", nullable = false)
	private BigDecimal revenue;

	@Column(name="remuneration_version_id", nullable = false)
	private Long remunerationVersionId;

}
