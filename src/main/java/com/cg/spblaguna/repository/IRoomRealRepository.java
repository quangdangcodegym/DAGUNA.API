package com.cg.spblaguna.repository;

import com.cg.spblaguna.model.Room;
import com.cg.spblaguna.model.RoomReal;
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

    @Query(value = "SELECT new com.cg.spblaguna.model.dto.req.RoomRealReqDTO ( " +
            "rrl.id, rrl.roomCode,rrl.statusRoom,rrl.eRangeRoom,rrl.floor,rrl.roomId.id )" +
            "FROM RoomReal rrl " +
            "WHERE NOT EXISTS ( " +
            "SELECT DISTINCT " +
            "bds.roomReal.id " +
            "FROM BookingDetail bds " +
            "WHERE (" +
            "bds.checkIn BETWEEN :selectFirstDay AND :selectLastDay " +
            "OR bds.checkOut BETWEEN :selectFirstDay AND :selectLastDay " +
            "OR :selectFirstDay BETWEEN bds.checkIn AND bds.checkOut " +
            "OR :selectLastDay BETWEEN bds.checkIn AND bds.checkOut )" +
            "AND rrl.id = bds.roomReal.id" +
            ")" +
            "AND (rrl.roomId.id= :roomId OR :roomId is null) ")
    List<RoomRealReqDTO> findAvailableRoomRealByCheckInAndCheckOut(@Param("selectFirstDay") LocalDateTime selectFirstDay,
                                                                   @Param("selectLastDay") LocalDateTime selectLastDay,
                                                                   @Param("roomId") Long roomId);

    @Query(value = "SELECT new com.cg.spblaguna.model.dto.req.RoomRealReqDTO ( " +
            "rrl.id, rrl.roomCode,rrl.statusRoom,rrl.eRangeRoom,rrl.floor,rrl.roomId.id )" +
            "FROM RoomReal rrl " +
            "WHERE rrl.id IN " +
            "(SELECT DISTINCT " +
            "bds.roomReal.id " +
            "FROM BookingDetail bds " +
            "WHERE (" +
            "bds.checkIn BETWEEN :selectFirstDay AND :selectLastDay " +
            "OR bds.checkOut BETWEEN :selectFirstDay AND :selectLastDay " +
            "OR :selectFirstDay BETWEEN bds.checkIn AND bds.checkOut " +
            "OR :selectLastDay BETWEEN bds.checkIn AND bds.checkOut )" +
            "AND rrl.id = bds.roomReal.id )" +
            "AND (rrl.roomId.id= :roomId OR :roomId IS NULL) ")
    List<RoomRealReqDTO> findUnAvailableRoomRealByCheckInAndCheckOut(@Param("selectFirstDay") LocalDateTime selectFirstDay,
                                                                     @Param("selectLastDay") LocalDateTime selectLastDay,
                                                                     @Param("roomId") Long roomId);

    @Query(value = "SELECT new com.cg.spblaguna.model.dto.req.RoomRealReqDTO ( " +
            "rrl.id, rrl.roomCode,rrl.statusRoom,rrl.eRangeRoom,rrl.floor,rrl.roomId.id )" +
            "FROM RoomReal rrl " +
            "WHERE NOT EXISTS ( " +
            "SELECT DISTINCT " +
            "bds.roomReal.id " +
            "FROM BookingDetail bds " +
            "WHERE (" +
            "bds.checkIn BETWEEN :selectFirstDay AND :selectLastDay " +
            "OR bds.checkOut BETWEEN :selectFirstDay AND :selectLastDay " +
            "OR :selectFirstDay BETWEEN bds.checkIn AND bds.checkOut " +
            "OR :selectLastDay BETWEEN bds.checkIn AND bds.checkOut )" +
            "AND rrl.id = bds.roomReal.id" +
            ")" +
            "AND (rrl.roomId.id= :roomId OR :roomId is null) " +
            "AND (rrl.id= :roomRealId OR :roomRealId is null)")
    List<RoomRealReqDTO> findAvailableRoomRealByCheckInAndCheckOutByRoomIdAndRoomReal(@Param("selectFirstDay") LocalDateTime selectFirstDay,
                                                                                      @Param("selectLastDay") LocalDateTime selectLastDay,
                                                                                      @Param("roomId") Long roomId,
                                                                                      @Param("roomRealId") Long roomRealId);
}
