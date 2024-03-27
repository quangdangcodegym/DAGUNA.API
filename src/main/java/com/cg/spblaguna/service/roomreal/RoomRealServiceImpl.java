package com.cg.spblaguna.service.roomreal;

import com.cg.spblaguna.exception.ResourceExistsException;
import com.cg.spblaguna.exception.ResourceNotFoundException;
import com.cg.spblaguna.model.Room;
import com.cg.spblaguna.model.RoomReal;
import com.cg.spblaguna.model.User;
import com.cg.spblaguna.model.dto.req.RoomRealReqDTO;
import com.cg.spblaguna.model.dto.res.RoomRealResDTO;
import com.cg.spblaguna.repository.IRoomRealRepository;
import com.cg.spblaguna.repository.IRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public RoomReal save(RoomReal roomReal) {
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

    @Override
    public List<RoomRealResDTO> getAllRoomRealResDTOBy(Long roomId, LocalDate checkIn, LocalDate checkOut) {
        return null;
    }

    @Override
    public List<RoomRealReqDTO> findRoomRealByCheckInAndCheckOut(LocalDateTime selectFirstDay, LocalDateTime selectLastDay, Long roomId) {
       try {
           List<RoomRealReqDTO> roomReals = roomRealRepository.findRoomRealByCheckInAndCheckOut(selectFirstDay,selectLastDay,roomId);
           if (roomReals == null || roomReals.isEmpty()) {
               throw new ResponseStatusException(HttpStatus.NOT_FOUND,"roomReals cannot be null or empty") ;
           }
           return roomReals;
       }catch (Exception e){
           e.printStackTrace();
           throw  new ResponseStatusException(HttpStatus.NO_CONTENT, "Error: Unexpected exception occurred");
       }
    }
}
