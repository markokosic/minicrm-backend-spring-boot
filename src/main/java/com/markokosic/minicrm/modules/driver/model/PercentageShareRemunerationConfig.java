package com.markokosic.minicrm.modules.driver.model;

import com.markokosic.minicrm.modules.driver.dto.request.CreatePercentageShareRemunerationConfigDTO;
import com.markokosic.minicrm.modules.driver.dto.request.CreateRemunerationRequestDTO;
import com.markokosic.minicrm.modules.remuneration.PercentageRemunerationCalculator;
import com.markokosic.minicrm.modules.remuneration.RemunerationModelType;
import com.markokosic.minicrm.modules.remuneration.RemunerationSplit;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "remuneration_percentage_configs")
@PrimaryKeyJoinColumn(name = "id")
@DiscriminatorValue("PERCENTAGE_SHARE")
public class PercentageShareRemunerationConfig extends DriverRemunerationConfig {

	@DecimalMin(value = "0.0", inclusive = true, message = "{driver.driverRevenueSharePercentage.invalid}")
	@DecimalMax(value = "100.0", message = "{driver.driverRevenueSharePercentage.invalid}")
	@Column(name="driver_revenue_share_percentage", nullable = false, precision = 5, scale = 4)
	private BigDecimal driverRevenueSharePercentage;

	@DecimalMin(value = "0.0", message = "{driver.minDriverPayout.negative}")
	@Column(name="min_driver_payout", precision = 19, scale = 2)
	private BigDecimal minDriverPayout;

	public void setDriverRevenueSharePercentage(BigDecimal driverRevenueSharePercentage) {
		if (driverRevenueSharePercentage == null) {
			this.driverRevenueSharePercentage = null;
			return;
		}
		if (driverRevenueSharePercentage.compareTo(BigDecimal.valueOf(100)) > 0) {
			this.driverRevenueSharePercentage = driverRevenueSharePercentage.divide(BigDecimal.valueOf(10000), 4, java.math.RoundingMode.HALF_UP);
		} else if (driverRevenueSharePercentage.compareTo(BigDecimal.ONE) > 0) {
			this.driverRevenueSharePercentage = driverRevenueSharePercentage.divide(BigDecimal.valueOf(100), 4, java.math.RoundingMode.HALF_UP);
		} else {
			this.driverRevenueSharePercentage = driverRevenueSharePercentage.setScale(4, java.math.RoundingMode.HALF_UP);
		}
	}

	@Override
	public RemunerationModelType getType() {
		return RemunerationModelType.PERCENTAGE_SHARE;
	}

	@Override
	public boolean isIdenticalTo(CreateRemunerationRequestDTO dto) {
		if (!(dto instanceof CreatePercentageShareRemunerationConfigDTO pDto)) {
			return false;
		}
		BigDecimal incoming = pDto.driverRevenueSharePercentage();
		if (incoming != null && incoming.compareTo(BigDecimal.valueOf(100)) > 0) {
			incoming = incoming.divide(BigDecimal.valueOf(10000), 4, java.math.RoundingMode.HALF_UP);
		} else if (incoming != null && incoming.compareTo(BigDecimal.ONE) > 0) {
			incoming = incoming.divide(BigDecimal.valueOf(100), 4, java.math.RoundingMode.HALF_UP);
		} else if (incoming != null) {
			incoming = incoming.setScale(4, java.math.RoundingMode.HALF_UP);
		}
		return areEqual(this.driverRevenueSharePercentage, incoming)
				&& areEqual(this.minDriverPayout, pDto.minDriverPayout());
	}


	@Override
	public RemunerationSplit calculateRemuneration(BigDecimal revenue) {
		return new PercentageRemunerationCalculator().calculateRemuneration(revenue, this);
	}


}
