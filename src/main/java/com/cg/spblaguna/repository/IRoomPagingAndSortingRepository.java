package com.cg.spblaguna.repository;

import com.cg.spblaguna.model.Room;
import com.cg.spblaguna.model.dto.res.RoomResDTO;
import com.cg.spblaguna.model.enumeration.ERoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface IRoomPagingAndSortingRepository extends PagingAndSortingRepository<Room, Long> {
    @Query("select " +
            "new com.cg.spblaguna.model.dto.res.RoomResDTO(r) " +
            "from Room r " +
            "where (:kw is null or (r.name like %:kw%)) " +
            "and (:roomType is null or r.roomType = :roomType) " )

    Page<RoomResDTO> filterRooms(
            @Param("kw") String kw,
            @Param("roomType") ERoomType roomType,
            Pageable pageable);

    @Query("select " +
            "new com.cg.spblaguna.model.dto.res.RoomResDTO(r) " +
            "from Room r " +
            "where (:kw is null or (r.name like %:kw%)) " +
            "and (:roomType is null or r.roomType = :roomType) " +
            "and (:minPrice is null or r.pricePerNight >= :minPrice) " +
            "and (:maxPrice is null or r.pricePerNight <= :maxPrice)")
    Page<RoomResDTO> filterRoomsByPrice(
            @Param("kw") String kw,
            @Param("roomType") ERoomType roomType,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            Pageable pageable);

}

