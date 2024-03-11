package com.cg.spblaguna.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppUtils {

    public BigDecimal calculateVAT(BigDecimal money, Float vat) {
        // moneyResult =  money + money * vat /100
        return money.add(money.multiply(new BigDecimal(vat)).divide(new BigDecimal(100)));
    }
    public Map<String, String> getValidationErrorJson(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return errors;
    }
}
