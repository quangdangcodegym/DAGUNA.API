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
}
