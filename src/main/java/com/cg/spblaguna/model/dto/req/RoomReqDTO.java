package com.cg.spblaguna.model.dto.req;

import com.cg.spblaguna.model.enumeration.ERoomType;
import com.cg.spblaguna.model.enumeration.EStatusRoom;
import com.cg.spblaguna.model.enumeration.EViewType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomReqDTO {
    private Long id;

    @NotBlank
    @Length(min = 5, max = 50)
    private String name;

    private ERoomType roomType;

    private Integer quantity;

    private EViewType viewType;

    private Long kindOfRoomId;

    private Long perTypId;
    @Positive(message = "Giá phòng phải lớn hơn 0")
    private BigDecimal pricePerNight;
    @Positive(message = "Diện tích phòng phải lớn hơn 0")
    private BigDecimal acreage;
    @Positive(message = "Số lượng người ở phải lớn hơn 0")
    private Integer sleep;
    @NotBlank(message = "Mô tả phòng không được để trống")
    private String description;
    @NotBlank(message = "Hãy thêm tiện ích cho phòng")
    private String utilitie;

    List<String> imageIds;

    public RoomReqDTO(Long id, String name, ERoomType roomType, Integer quantity, EViewType viewType, Long kindOfRoomId, Long perTypId, BigDecimal pricePerNight, BigDecimal acreage, Integer sleep, String description, String utilitie) {
        this.id = id;
        this.name = name;
        this.roomType = roomType;
        this.quantity = quantity;
        this.viewType = viewType;
        this.kindOfRoomId = kindOfRoomId;
        this.perTypId = perTypId;
        this.pricePerNight = pricePerNight;
        this.acreage = acreage;
        this.sleep = sleep;
        this.description = description;
        this.utilitie = utilitie;
    }

}
