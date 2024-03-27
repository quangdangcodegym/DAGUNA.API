package com.cg.spblaguna.controller.api;

import com.cg.spblaguna.model.RoomReal;
import com.cg.spblaguna.model.dto.req.SearchAvailableRoomReqDTO;
import com.cg.spblaguna.model.dto.res.RoomRealResDTO;
import com.cg.spblaguna.service.room.IRoomService;
import com.cg.spblaguna.service.room.RoomServiceImpl;
import com.cg.spblaguna.service.roomreal.IRoomRealService;
import com.cg.spblaguna.service.roomreal.RoomRealServiceImpl;
import com.cg.spblaguna.util.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/room-reals")
@CrossOrigin(origins = "*")
public class RoomRealAPI {
    @Autowired
    private IRoomRealService roomRealService;
    @Autowired
    private AppUtils appUtils;
    @Autowired
    private IRoomService roomService;
    @GetMapping
    public ResponseEntity<?> getAllRoomReal(){
        List<RoomReal> roomReals= roomRealService.findAll();
        return new ResponseEntity<>(roomReals, HttpStatus.OK);
    }
    @GetMapping("/{roomId}")
    public List<RoomReal> findByRoomId(@PathVariable Long roomId) {
        return roomRealService.findAllRoomRealsByRoomId(roomId);
    }

    @PostMapping("/available")
    public ResponseEntity<?> getAvailableRoom(@RequestBody SearchAvailableRoomReqDTO searchAvailableRoomReqDTO) {
        LocalDateTime checkIn = searchAvailableRoomReqDTO.getCheckIn();
        LocalDateTime checkOut = searchAvailableRoomReqDTO.getCheckOut();
        Long roomId = searchAvailableRoomReqDTO.getRoomId();
        List<RoomRealResDTO> availableRooms = roomRealService.getAvailable(checkIn, checkOut, roomId);
        return ResponseEntity.ok(availableRooms);
    }


}
