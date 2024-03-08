package com.cg.spblaguna.service.room;

import com.cg.spblaguna.model.Room;
import com.cg.spblaguna.service.IGeneralService;

import java.util.List;

public interface IRoomService extends IGeneralService<Room,Long> {
    List<Room> findAllByUser_Unlock(boolean user_unlock);
    Room findByUser_Id(Long id);
    void update(Room room);
}
