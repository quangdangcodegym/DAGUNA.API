package com.cg.spblaguna.model.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomFindForCheckInAndCheckOutReqDTO {
    private LocalDateTime selectFirstDay;
    private LocalDateTime selectLastDay;
}
