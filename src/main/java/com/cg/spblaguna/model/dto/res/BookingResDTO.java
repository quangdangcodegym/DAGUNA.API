package com.cg.spblaguna.model.dto.res;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookingResDTO {
    private Long bookingId;
    private List<BookingDetailResDTO> bookingDetails;
}
