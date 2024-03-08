package com.cg.spblaguna.model.dto.req;

import com.cg.spblaguna.model.enumeration.EBookingServiceType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookingServiceDTO {
    private Long bookingServiceId;
    private EBookingServiceType bookingServiceType;

    private Long numberPersonOrCar;
    private LocalDate dateChooseService;
}
