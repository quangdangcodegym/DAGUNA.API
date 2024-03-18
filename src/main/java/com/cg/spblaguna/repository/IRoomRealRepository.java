package com.cg.spblaguna.repository;

import com.cg.spblaguna.model.Room;
import com.cg.spblaguna.model.RoomReal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IRoomRealRepository extends JpaRepository<RoomReal, Long> {

//    @Query(value = "select * from room_reals as rl left join rooms as r on rl.room_id = r.room_id where rl.room_id = :roomId", nativeQuery = true)
    List<RoomReal> findAllByRoomId(Room roomId);
}
