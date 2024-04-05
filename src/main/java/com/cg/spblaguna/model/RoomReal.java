package com.cg.spblaguna.model;

import com.cg.spblaguna.model.dto.res.RoomRealResDTO;
import com.cg.spblaguna.model.enumeration.ERangeRoom;
import com.cg.spblaguna.model.enumeration.EStatusRoom;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "room_reals")
@Accessors(chain = true)
public class RoomReal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomCode;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room roomId;
    @Enumerated(EnumType.STRING)
    @Column(name = "status_room")
    private EStatusRoom statusRoom;

    @Enumerated(EnumType.STRING)
    @Column(name = "range_room")
    private ERangeRoom eRangeRoom;
    private Integer floor;

    public RoomRealResDTO toRoomRealResDTO(){
        RoomRealResDTO roomRealResDTO = new RoomRealResDTO();
        roomRealResDTO.setIdRoom(this.roomId.getId());
        roomRealResDTO.setId(this.id);
        roomRealResDTO.setRoomCode(this.getRoomCode());
        roomRealResDTO.setStatusRoom(this.getStatusRoom());
        roomRealResDTO.setERangeRoom(this.getERangeRoom());
        roomRealResDTO.setFloor(this.getFloor());
        return roomRealResDTO;
    }
}
