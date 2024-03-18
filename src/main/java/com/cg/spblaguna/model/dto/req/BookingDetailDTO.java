package com.cg.spblaguna.model.dto.req;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class BookingDetailDTO {
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Long roomId;
    private Integer numberAdult;
    private Integer numberChildren;
    private String discountCode;
    private BigDecimal totalAmount;
    private BigDecimal price;
    private BigDecimal total;
}
