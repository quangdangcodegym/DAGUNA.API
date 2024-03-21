package com.cg.spblaguna.service.roomreal;

import com.cg.spblaguna.model.Room;
import com.cg.spblaguna.model.RoomReal;
import com.cg.spblaguna.model.User;
import com.cg.spblaguna.repository.IRoomRealRepository;
import com.cg.spblaguna.repository.IRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoomRealServiceImpl implements IRoomRealService {
    @Autowired
    private IRoomRealRepository roomRealRepository;

    @Autowired
    private IRoomRepository roomRepository;


    @Override
    public List<RoomReal> findAll() {
        return roomRealRepository.findAll();
    }


    @Override
    public Optional<RoomReal> findById(Long id) {
        return Optional.empty();
    }


    @Override
    public User save(RoomReal roomReal) {
        roomRealRepository.save(roomReal);
        return null;
    }

    @Override
    public void deleteById(Long id) {
        roomRealRepository.deleteById(id);
    }

    public List<RoomReal> findAllRoomRealsByRoomId(Long roomId) {
        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isEmpty()) {
            throw new RuntimeException();
        }
        Room currentRoom = room.get();
        return roomRealRepository.findAllByRoomId(currentRoom);
    }
}
