package com.cg.spblaguna.model.dto.res;

import com.cg.spblaguna.model.*;
import com.cg.spblaguna.model.dto.req.ImageReqDTO;
import com.cg.spblaguna.model.enumeration.ERoomType;
import com.cg.spblaguna.model.enumeration.EStatusRoom;
import com.cg.spblaguna.model.enumeration.EViewType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

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

    private KindOfRoom kindOfRoom;

    private PerType perType;

    private BigDecimal pricePerNight;

    private BigDecimal acreage;

    private Integer sleep;

    private String description;

    private String utilitie;

    private Rate rate;
    private List<ImageResDTO> imageResDTOS;
}
