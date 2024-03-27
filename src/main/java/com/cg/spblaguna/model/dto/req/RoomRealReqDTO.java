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
@AllArgsConstructor
@NoArgsConstructor
public class RoomRealReqDTO {
    private Long id;
    private String roomCode;
    private EStatusRoom statusRoom;
    private ERangeRoom rangeRoom;
    private Integer floor;
    private Long roomId;


}
