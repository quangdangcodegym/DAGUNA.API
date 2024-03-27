package com.cg.spblaguna.service.roomreal;

import com.cg.spblaguna.model.RoomReal;
import com.cg.spblaguna.model.dto.res.RoomRealResDTO;
import com.cg.spblaguna.service.IGeneralService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IRoomRealService extends IGeneralService <RoomReal, Long> {
    List<RoomReal> findAllRoomRealsByRoomId(Long roomId);
        List<RoomRealResDTO> getAvailable(LocalDateTime checkIn,
                                           LocalDateTime checkOut,
                                          Long roomId);}

