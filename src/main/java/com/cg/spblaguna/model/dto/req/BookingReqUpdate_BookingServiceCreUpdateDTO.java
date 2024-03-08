package com.cg.spblaguna.model.dto.req;

import com.cg.spblaguna.model.enumeration.EBookingServiceType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Được dùng khi thêm 1 service vào booking
 */
@Getter
@Setter
public class BookingReqUpdate_BookingServiceCreUpdateDTO {
    private Long bookingDetailId;
    private Long bookingServiceId;
    private EBookingServiceType bookingServiceType;
    private Long numberCarOrPerson;
    private LocalDate dateChooseService;

}
