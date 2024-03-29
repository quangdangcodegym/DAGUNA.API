package com.cg.spblaguna.controller.api;

import com.cg.spblaguna.model.RoomReal;
import com.cg.spblaguna.model.dto.req.RoomRealFindForCheckInAndCheckOutReqDTO;
import com.cg.spblaguna.model.dto.req.RoomRealReqDTO;
import com.cg.spblaguna.service.roomreal.RoomRealServiceImpl;
import com.cg.spblaguna.util.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/room-reals")
@CrossOrigin(origins = "*")
public class RoomRealAPI {
    @Autowired
    private RoomRealServiceImpl roomRealService;
    @Autowired
    private AppUtils appUtils;

    @GetMapping
    public ResponseEntity<?> getAllRoomReal() {
        List<RoomReal> roomReals = roomRealService.findAll();
        return new ResponseEntity<>(roomReals, HttpStatus.OK);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<?> findByRoomId(@PathVariable Long roomId) {
        List<RoomReal> roomReals = roomRealService.findAllRoomRealsByRoomId(roomId);
        return new ResponseEntity<>(roomReals, HttpStatus.OK);
    }

    @PostMapping("/find-available-room-real")
    public ResponseEntity<?> findAvailableRoomRealByCheckInAndCheckOut(@RequestBody RoomRealFindForCheckInAndCheckOutReqDTO roomRealFindForCheckInAndCheckOutReqDTO,
                                                                       @RequestParam(required = false) Long roomId) {
        LocalDateTime selectFirstDay = roomRealFindForCheckInAndCheckOutReqDTO.getSelectFirstDay();
        LocalDateTime selectLastDay = roomRealFindForCheckInAndCheckOutReqDTO.getSelectLastDay();
        List<RoomRealReqDTO> roomRealReqDTOS = roomRealService.findAvailableRoomRealByCheckInAndCheckOut(selectFirstDay, selectLastDay, roomId);
        return new ResponseEntity<>(roomRealReqDTOS, HttpStatus.OK);
    }

    @PostMapping("/findUn-available-room-real")
    public ResponseEntity<?> findUnAvailableRoomRealByCheckInAndCheckOut(@RequestBody RoomRealFindForCheckInAndCheckOutReqDTO roomRealFindForCheckInAndCheckOutReqDTO,
                                                                         @RequestParam(required = false) Long roomId) {
        LocalDateTime selectFirstDay = roomRealFindForCheckInAndCheckOutReqDTO.getSelectFirstDay();
        LocalDateTime selectLastDay = roomRealFindForCheckInAndCheckOutReqDTO.getSelectLastDay();
        List<RoomRealReqDTO> roomRealReqDTOS = roomRealService.findUnAvailableRoomRealByCheckInAndCheckOut(selectFirstDay, selectLastDay, roomId);
        return new ResponseEntity<>(roomRealReqDTOS, HttpStatus.OK);
    }

    @PostMapping("/find-available-room-real-detail")
    public ResponseEntity<?> findAvailableRoomRealByCheckInAndCheckOutByRoomIdAndRoomReal
            (@RequestBody RoomRealFindForCheckInAndCheckOutReqDTO roomRealFindForCheckInAndCheckOutReqDTO,
             @RequestParam(required = false) Long roomId, @RequestParam(required = false) Long roomRealId) {
        LocalDateTime selectFirstDay = roomRealFindForCheckInAndCheckOutReqDTO.getSelectFirstDay();
        LocalDateTime selectLastDay = roomRealFindForCheckInAndCheckOutReqDTO.getSelectLastDay();
        List<RoomRealReqDTO> roomRealReqDTOS = roomRealService.findAvailableRoomRealByCheckInAndCheckOutByRoomIdAndRoomReal(selectFirstDay, selectLastDay, roomId,roomRealId);
        return new ResponseEntity<>(roomRealReqDTOS, HttpStatus.OK);
    }
}
