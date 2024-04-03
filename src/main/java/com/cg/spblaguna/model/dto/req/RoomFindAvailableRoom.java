package com.cg.spblaguna.model.dto.req;

import com.cg.spblaguna.model.Room;
import com.cg.spblaguna.model.enumeration.ERoomType;
import com.cg.spblaguna.model.enumeration.EViewType;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomFindAvailableRoom {
    private Long id;

    private String name;

    private ERoomType roomType;

    private Integer quantity;

    private EViewType viewType;

    private Long kindOfRoomId;

    private Long perTypId;
    private BigDecimal pricePerNight;
    private BigDecimal acreage;
    private Integer sleep;
    private String description;
    private String utilitie;
    private Long totalCount;

    public RoomFindAvailableRoom(Room room, Long totalCount) {
        this.id = room.getId();
        this.utilitie = room.getUtilitie();
        this.name = room.getName();
        this.roomType = room.getRoomType();
        this.quantity = room.getQuantity();
        this.viewType = room.getViewType();
        this.kindOfRoomId = room.getKindOfRoom().getId();
        this.perTypId = room.getPerType().getId();
        this.pricePerNight = room.getPricePerNight();
        this.acreage = room.getAcreage();
        this.sleep = room.getSleep();
        this.description = room.getDescription();
        this.totalCount = totalCount;

    }
}
