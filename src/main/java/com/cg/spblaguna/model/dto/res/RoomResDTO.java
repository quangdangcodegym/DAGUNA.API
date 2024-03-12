package com.cg.spblaguna.model.dto.res;

import com.cg.spblaguna.model.*;
import com.cg.spblaguna.model.dto.req.ImageReqDTO;
import com.cg.spblaguna.model.enumeration.ERoomType;
import com.cg.spblaguna.model.enumeration.EStatusRoom;
import com.cg.spblaguna.model.enumeration.EViewType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class RoomResDTO {
    private Long id;

    private String name;

    private ERoomType roomType;

    private EStatusRoom statusRoom;

    private EViewType viewType;

    private KindOfRoomResDTO kindOfRoom;

    private PerTypeResDTO perType;

    private BigDecimal pricePerNight;

    private BigDecimal acreage;

    private Integer sleep;

    private String description;

    private String utilitie;

    private Rate rate;
    private List<ImageResDTO> imageResDTOS;

    public RoomResDTO(Room room) {
        this.setId(room.getId());
        this.setName(room.getName());
        this.setRoomType(room.getRoomType());
        this.setStatusRoom(room.getStatusRoom());
        this.setViewType(room.getViewType());
        this.setKindOfRoom(room.getKindOfRoom().toKindOfRoomResDTO());
        this.setPerType(room.getPerType().toPerTypeResDTO());
        this.setPricePerNight(room.getPricePerNight());
        this.setAcreage(room.getAcreage());
        this.setSleep(room.getSleep());
        this.setDescription(room.getDescription());
        this.setUtilitie(room.getUtilitie());
        List<ImageResDTO> imageResDTOS = room.getImages()
                .stream()
                .map(m -> {
                    ImageResDTO imageResDTO = new ImageResDTO();
                    imageResDTO.setId(m.getId());
                    imageResDTO.setFileUrl(m.getFileUrl());
                    return imageResDTO;
                })
                .collect(Collectors.toList());
        this.setImageResDTOS(imageResDTOS);
    }


}
