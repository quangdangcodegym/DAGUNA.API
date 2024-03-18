package com.cg.spblaguna.model.dto.res;

import com.cg.spblaguna.model.BookingDetail;
import com.cg.spblaguna.model.BookingService;
import com.cg.spblaguna.model.enumeration.EBookingServiceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDetailServiceResDTO {
    private Long id;
    private BookingServiceResDTO bookingService;
    private EBookingServiceType bookingServiceType;
    private Long numberCar;
    private Long numberPerson;
    private LocalDate dateChooseService;
    private BigDecimal vat;
    private BigDecimal total;
    private BigDecimal price;
}
