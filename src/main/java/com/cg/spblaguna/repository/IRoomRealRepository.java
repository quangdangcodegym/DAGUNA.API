package com.cg.spblaguna.repository;

import com.cg.spblaguna.model.Room;
import com.cg.spblaguna.model.RoomReal;

import com.cg.spblaguna.model.dto.res.RoomRealResDTO;


import com.cg.spblaguna.model.dto.req.RoomRealReqDTO;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IRoomRealRepository extends JpaRepository<RoomReal, Long> {

    //    @Query(value = "select * from room_reals as rl left join rooms as r on rl.room_id = r.room_id where rl.room_id = :roomId", nativeQuery = true)
    List<RoomReal> findAllByRoomId(Room roomId);

    boolean existsByRoomCode(String roomCode);


    @Query("SELECT new com.cg.spblaguna.model.dto.res.RoomRealResDTO(r.id,r.roomCode,r.statusRoom,r.eRangeRoom,r.floor) FROM RoomReal r WHERE r.id NOT IN (" +
            "SELECT bd.roomReal.id FROM BookingDetail bd WHERE " +
            "(:checkIn BETWEEN bd.checkIn AND bd.checkOut) OR " +
            "(:checkOut BETWEEN bd.checkIn AND bd.checkOut) OR " +
            "(bd.checkIn < :checkIn AND bd.checkOut > :checkOut)) AND r.roomId.id = :roomId")
    List<RoomRealResDTO> getAvailable(@Param("checkIn") LocalDateTime checkIn,
                                      @Param("checkOut") LocalDateTime checkOut,
                                      @Param("roomId") Long roomId);

    @Query(value = "SELECT new com.cg.spblaguna.model.dto.req.RoomRealReqDTO (" +
            "rrl.id, rrl.roomCode,rrl.statusRoom,rrl.eRangeRoom,rrl.floor,rrl.roomId.id )" +
            "FROM RoomReal rrl " +
            "WHERE rrl.id NOT IN ( " +
            "SELECT DISTINCT " +
            "bds.roomReal.id " +
            "FROM BookingDetail bds " +
            "WHERE (" +
            "bds.checkIn BETWEEN :selectFirstDay AND :selectLastDay " +
            "OR bds.checkOut BETWEEN :selectFirstDay AND :selectLastDay " +
            "OR :selectFirstDay BETWEEN bds.checkIn AND bds.checkOut " +
            "OR :selectLastDay BETWEEN bds.checkIn AND bds.checkOut ))" +
            "AND (rrl.roomId.id= :roomId OR :roomId is null) " +
            "UNION " +
            "SELECT new com.cg.spblaguna.model.dto.req.RoomRealReqDTO ( " +
            " rrl.id, rrl.roomCode,rrl.statusRoom,rrl.eRangeRoom,rrl.floor,rrl.roomId.id )" +
            "FROM RoomReal  rrl " +
            "WHERE  rrl.id NOT IN (" +
            "SELECT DISTINCT bds2.roomReal.id " +
            "FROM BookingDetail bds2 " +
            "WHERE bds2.roomReal.id = rrl.id)" +
            "AND (rrl.roomId.id= :roomId OR :roomId is null )"
    )
    List<RoomRealReqDTO> findRoomRealByCheckInAndCheckOut(@Param("selectFirstDay") LocalDateTime selectFirstDay,
                                                          @Param("selectLastDay") LocalDateTime selectLastDay,
                                                          @Param("roomId") Long roomId);

}
