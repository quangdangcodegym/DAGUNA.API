package com.cg.spblaguna.model.dto.res;

import com.cg.spblaguna.model.BookingService;
import com.cg.spblaguna.model.enumeration.EBookingServiceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingServiceResDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private EBookingServiceType bookingServiceType;

    public BookingServiceResDTO(BookingService bookingService){
        this.id = bookingService.getId();
        this.name = bookingService.getName();
        this.description = bookingService.getDescription();
        this.price = bookingService.getPrice();
        this.bookingServiceType = bookingService.getBookingServiceType();
    }
}
