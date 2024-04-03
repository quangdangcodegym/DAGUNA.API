package com.cg.spblaguna.model.dto.req;

import com.cg.spblaguna.model.enumeration.EMethod;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class DepositReqDTO {
    private EMethod method;
    private BigDecimal depositedAmount;
    private Long transfer_id;
    private Long bookingId;

    public DepositReqDTO(EMethod method, BigDecimal depositedAmount, Long transfer_id, Long bookingId) {
        this.method = method;
        this.depositedAmount = depositedAmount;
        this.transfer_id = transfer_id;
        this.bookingId = bookingId;
    }
}
