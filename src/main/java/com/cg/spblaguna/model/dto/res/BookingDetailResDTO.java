package com.cg.spblaguna.model.dto.res;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class BookingDetailResDTO {
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private BigDecimal totalAmount;
    private BigDecimal total;
    private BigDecimal vat;
    private Integer numberAdult;
    private Integer numberChildren;
    private String discountCode;

    private RoomResDTO room;
}
