package com.cg.spblaguna.model.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingReqUpdate_RoomAddDTO {
    private Long bookingId;
    private BookingDetailDTO bookingDetail;
}
