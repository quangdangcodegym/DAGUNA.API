package com.cg.spblaguna.model.dto.req;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SearchAvailableRoomReqDTO {
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Long roomId;
}
