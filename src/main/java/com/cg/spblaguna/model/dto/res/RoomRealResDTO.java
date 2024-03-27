package com.cg.spblaguna.model.dto.res;

import com.cg.spblaguna.model.Room;
import com.cg.spblaguna.model.enumeration.ERangeRoom;
import com.cg.spblaguna.model.enumeration.EStatusRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class RoomRealResDTO {
    private Long id;
    private String roomCode;
    private EStatusRoom statusRoom;
    private ERangeRoom eRangeRoom;
    private Integer floor;

    public RoomRealResDTO(Long id, String roomCode, EStatusRoom statusRoom, ERangeRoom eRangeRoom, Integer floor) {
        this.id = id;
        this.roomCode = roomCode;
        this.statusRoom = statusRoom;
        this.eRangeRoom = eRangeRoom;
        this.floor = floor;
    }
}
