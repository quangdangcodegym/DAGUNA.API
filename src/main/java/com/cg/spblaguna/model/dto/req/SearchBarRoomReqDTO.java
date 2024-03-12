package com.cg.spblaguna.model.dto.req;

import com.cg.spblaguna.model.PerType;
import com.cg.spblaguna.model.enumeration.ERoomType;
import com.cg.spblaguna.model.enumeration.EViewType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class SearchBarRoomReqDTO {

    @Getter
    @Setter
    public static class GuestReqDTO{
        private Long numberAdult;
        private Long numberChildren;
    }
    private GuestReqDTO guest;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private EViewType viewType;          // ALL: se null, GARDEN_VIEW, SEA_VIEW
    private Long perType;               // -1: All
    private ERoomType roomType;        // ALL: sẽ null, SUPERIOR, SUPERIOR_PLUS, HONEYMOON_BUNGALOW
    private BigDecimal priceMin;
    private BigDecimal priceMax;


}
