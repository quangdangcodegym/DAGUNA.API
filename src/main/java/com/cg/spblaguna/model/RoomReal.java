package com.cg.spblaguna.model;

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
    private Room roomId;
    @Enumerated(EnumType.STRING)
    @Column(name = "status_room")
    private EStatusRoom statusRoom;
    private ERangeRoom eRangeRoom;
    private Integer floor;

}
