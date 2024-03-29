package com.cg.spblaguna.model.dto.res;

import com.cg.spblaguna.model.Booking;
import com.cg.spblaguna.model.BookingService;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
public class BookingDetailResDTO {
    private Long bookingDetailId;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime checkIn;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime checkOut;
    private BigDecimal totalAmount;
    private BigDecimal total;
    private BigDecimal vat;
    private Integer numberAdult;
    private String childrenAge;
    private String discountCode;
    private Boolean checkInStatus;
    private RoomResDTO room;
    private List<BookingDetailServiceResDTO> bookingDetailServiceResDTOS;

    private RoomRealResDTO roomReal;

    public BookingDetailResDTO(Long bookingDetailId, LocalDateTime checkIn, LocalDateTime checkOut, BigDecimal totalAmount,
                               BigDecimal total, BigDecimal vat, Integer numberAdult, String childrenAge, String discountCode, Boolean checkInStatus,
                               RoomResDTO room, List<BookingDetailServiceResDTO> bookingDetailServiceResDTOS) {
        this.bookingDetailId = bookingDetailId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalAmount = totalAmount;
        this.total = total;
        this.vat = vat;
        this.numberAdult = numberAdult;
        this.childrenAge = childrenAge;
        this.discountCode = discountCode;
        this.checkInStatus = checkInStatus;
        this.room = room;
        this.bookingDetailServiceResDTOS = bookingDetailServiceResDTOS;
    }
    public BookingDetailResDTO(Long bookingDetailId, LocalDateTime checkIn, LocalDateTime checkOut, BigDecimal totalAmount,
                               BigDecimal total, BigDecimal vat, Integer numberAdult, String childrenAge, String discountCode, Boolean checkInStatus,
                               RoomResDTO room, List<BookingDetailServiceResDTO> bookingDetailServiceResDTOS,
                                RoomRealResDTO roomRealResDTO) {
        this.bookingDetailId = bookingDetailId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalAmount = totalAmount;
        this.total = total;
        this.vat = vat;
        this.numberAdult = numberAdult;
        this.childrenAge = childrenAge;
        this.discountCode = discountCode;
        this.checkInStatus = checkInStatus;
        this.room = room;
        this.bookingDetailServiceResDTOS = bookingDetailServiceResDTOS;
        this.roomReal = roomRealResDTO;
    }



}
