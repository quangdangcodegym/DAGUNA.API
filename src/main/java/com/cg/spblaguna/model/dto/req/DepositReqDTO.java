package com.cg.spblaguna.model.dto.req;

import com.cg.spblaguna.model.enumeration.EBank;
import com.cg.spblaguna.model.enumeration.EMethod;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class DepositReqDTO {
    private EMethod method;
    private BigDecimal depositedAmount;
    private Long transfer_id;
    private Long bookingId;
    private BigDecimal total;
    private EBank bank;
    private LocalDate transferDate;
}
