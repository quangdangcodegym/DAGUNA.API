package com.cg.spblaguna.model.dto.res;

import com.cg.spblaguna.model.Booking;
import com.cg.spblaguna.model.BookingService;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class BookingDetailResDTO {
    private Long bookingDetailId;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private BigDecimal totalAmount;
    private BigDecimal total;
    private BigDecimal vat;
    private Integer numberAdult;
    private Integer numberChildren;
    private String discountCode;

    private RoomResDTO room;

    private List<BookingDetailServiceResDTO> bookingDetailServiceResDTOS;



}
