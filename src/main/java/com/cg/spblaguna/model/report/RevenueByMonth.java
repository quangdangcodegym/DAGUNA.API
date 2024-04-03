package com.cg.spblaguna.model.report;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface RevenueByMonth {
    String getMonth_Year();
    BigDecimal getTotal_Amount();
}
