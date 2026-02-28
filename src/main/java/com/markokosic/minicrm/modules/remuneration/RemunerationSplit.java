package com.markokosic.minicrm.modules.remuneration;

import java.math.BigDecimal;

public record RemunerationSplit(BigDecimal companyRemuneration, BigDecimal driverRemuneration) {}