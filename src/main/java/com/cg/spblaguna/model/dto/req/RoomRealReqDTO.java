package com.cg.spblaguna.model.dto.req;

import com.cg.spblaguna.model.Room;
import com.cg.spblaguna.model.enumeration.ERangeRoom;
import com.cg.spblaguna.model.enumeration.EStatusRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@NoArgsConstructor
public class RoomRealReqDTO {
    private Long id;
    private String roomCode;
    private EStatusRoom statusRoom;
    private ERangeRoom rangeRoom;
    private Integer floor;
    private Long roomId;

    public RoomRealReqDTO(Long id, String roomCode, EStatusRoom statusRoom, ERangeRoom rangeRoom, Integer floor, Long roomId) {
        this.id = id;
        this.roomCode = roomCode;
        this.statusRoom = statusRoom;
        this.rangeRoom = rangeRoom;
        this.floor = floor;
        this.roomId = roomId;
    }
}
