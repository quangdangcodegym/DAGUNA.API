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
@AllArgsConstructor
@Accessors(chain = true)
public class RoomRealResDTO {
    private Long id;
    private String roomCode;
    private Room roomId;
    private EStatusRoom statusRoom;
    private ERangeRoom eRangeRoom;
    private Integer floor;
    private Long idRoom;

    public RoomRealResDTO(Long id, String roomCode,Room roomId, EStatusRoom statusRoom, ERangeRoom eRangeRoom, Integer floor){
        this.id = id;
        this.roomCode = roomCode;
        this.roomId = roomId;
        this.statusRoom = statusRoom;
        this.eRangeRoom = eRangeRoom;
        this.floor = floor;
    }
}
