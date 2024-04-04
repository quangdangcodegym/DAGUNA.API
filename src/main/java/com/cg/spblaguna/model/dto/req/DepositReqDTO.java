package com.cg.spblaguna.model.dto.req;
import com.cg.spblaguna.model.enumeration.EBank;
import com.cg.spblaguna.model.enumeration.EMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepositReqDTO {
    private EMethod method;
    private BigDecimal depositedAmount;
    private Long transferId;
    private Long bookingId;
    private BigDecimal total;
    private EBank bank;
    private LocalDate transferDate;
    private String note;
}
