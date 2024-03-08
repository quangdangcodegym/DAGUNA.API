package com.cg.spblaguna.util;

import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class AppUtils {

    public BigDecimal calculateVAT(BigDecimal money, Float vat) {
        // moneyResult =  money + money * vat /100
        return money.add(money.multiply(new BigDecimal(vat)).divide(new BigDecimal(100)));
    }
}
