package com.cg.spblaguna.service.roomreal;

import com.cg.spblaguna.model.RoomReal;
import com.cg.spblaguna.model.dto.req.RoomRealReqDTO;
import com.cg.spblaguna.model.dto.res.RoomRealResDTO;
import com.cg.spblaguna.service.IGeneralService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IRoomRealService extends IGeneralService <RoomReal, Long> {

    List<RoomRealResDTO> getAllRoomRealResDTOBy(Long roomId, LocalDate checkIn, LocalDate checkOut);
 List<RoomRealReqDTO> findRoomRealByCheckInAndCheckOut(LocalDateTime selectFirstDay, LocalDateTime selectLastDay,Long roomId);
}
