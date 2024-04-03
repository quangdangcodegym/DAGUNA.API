package com.cg.spblaguna.model.dto.res;

import com.cg.spblaguna.model.Booking;
import com.cg.spblaguna.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingResDTO {
    private Long bookingId;
    private List<BookingDetailResDTO> bookingDetails;
    private String bookingCode;

    private CustomerInfoResDTO customerInfo;

    private BigDecimal total;

    public BookingResDTO(Long bookingId, List<BookingDetailResDTO> bookingDetails, String bookingCode, CustomerInfoResDTO customerInfo) {
        this.bookingId = bookingId;
        this.bookingDetails = bookingDetails;
        this.bookingCode = bookingCode;
        this.customerInfo = customerInfo;
    }

    public BookingResDTO(Booking booking) {
        this.setBookingId(booking.getId());
       this.setBookingCode(booking.getBookingCode());

       this.setTotal(booking.getTotal());
        List<BookingDetailResDTO> bookingDetailResDTOS = booking.getBookingDetails().stream()
                .map(bdt -> bdt.toBookingDetailResDTO())
                .collect(Collectors.toList());
        this.setBookingDetails(bookingDetailResDTOS);

    }

}
