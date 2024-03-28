package com.cg.spblaguna.repository;

import com.cg.spblaguna.model.Room;
import com.cg.spblaguna.model.dto.req.RoomFindAvailableRoom;
import com.cg.spblaguna.model.dto.req.RoomReqDTO;
import com.cg.spblaguna.model.dto.res.RoomResDTO;
import com.cg.spblaguna.model.enumeration.ERoomType;
import com.cg.spblaguna.model.enumeration.EStatusRoom;
import com.cg.spblaguna.model.enumeration.EViewType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IRoomRepository extends JpaRepository<Room, Long> {
    // pageable: localhost:8080/api/rooms?page=3&size=5
    // pageable (page, size)
    @Query("select " +
            "new com.cg.spblaguna.model.dto.res.RoomResDTO(r) " +
            "from Room  r where r.sleep >= :sleepNumber and (:roomType is null or r.roomType = :roomType) " +
            "and (:viewType is null or r.viewType = :viewType) " +
            "and (:perType = -1L or r.perType.id = :perType) " +
            "and (r.pricePerNight >= :priceMin and r.pricePerNight <= :priceMax) ")
    Page<RoomResDTO> searchBarRoomReqDTO(@Param("sleepNumber") Long sleepNumber, @Param("roomType") ERoomType roomType,
                                         @Param("perType") Long perType, @Param("viewType") EViewType viewType,
                                         @Param("priceMin") BigDecimal priceMin, @Param("priceMax") BigDecimal priceMax,
                                         Pageable pageable
    );
    @Query("select " +
            "new com.cg.spblaguna.model.dto.res.RoomResDTO(r) " +
            "from Room  r")
    Page<RoomResDTO> searchBarFake(Pageable pageable);

    @Query("select " +
            "new com.cg.spblaguna.model.dto.res.RoomResDTO(r) " +
            "from Room  r where r.sleep >= :sleepNumber and (:roomType is null or r.roomType = :roomType) " +
            "and (:viewType is null or r.viewType = :viewType) " +
            "and (:perType = -1L or r.perType.id = :perType) " +
            "and (r.pricePerNight >= :priceMin and r.pricePerNight <= :priceMax) ")
    List<RoomResDTO> searchBarRoomReqDTO(@Param("sleepNumber") Long sleepNumber, @Param("roomType") ERoomType roomType,
                                         @Param("perType") Long perType, @Param("viewType") EViewType viewType,
                                         @Param("priceMin") BigDecimal priceMin, @Param("priceMax") BigDecimal priceMax
    );

    //
    @Query(value = "SELECT new com.cg.spblaguna.model.dto.req.RoomFindAvailableRoom(r, COUNT(r) ) " +
            "FROM " +
            "RoomReal rrl " +
            "JOIN Room r ON r.id = rrl.roomId.id " +
            "WHERE " +
            "NOT EXISTS( SELECT DISTINCT bds.roomReal.id " +
            "FROM BookingDetail bds " +
            "WHERE (bds.checkIn BETWEEN :selectFirstDay AND :selectLastDay " +
            "OR bds.checkOut BETWEEN :selectFirstDay AND :selectLastDay " +
            "OR :selectFirstDay BETWEEN bds.checkIn AND bds.checkOut " +
            "OR :selectLastDay BETWEEN bds.checkIn AND bds.checkOut) " +
            "AND rrl.id = bds.roomReal.id) " +
            "GROUP BY rrl.roomId.id ")
    List<RoomFindAvailableRoom> findAvailableRoom(@Param("selectFirstDay") LocalDateTime selectFirstDay,
                                                  @Param("selectLastDay") LocalDateTime selectLastDay);



}
