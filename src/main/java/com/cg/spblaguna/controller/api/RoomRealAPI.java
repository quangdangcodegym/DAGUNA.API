package com.cg.spblaguna.controller.api;

import com.cg.spblaguna.model.RoomReal;
import com.cg.spblaguna.model.dto.req.RoomRealFindForCheckInAndCheckOutReqDTO;
import com.cg.spblaguna.model.dto.req.RoomRealReqDTO;
import com.cg.spblaguna.model.dto.res.RoomRealResDTO;
import com.cg.spblaguna.service.roomreal.RoomRealServiceImpl;
import com.cg.spblaguna.util.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    public List<RoomReal> findByRoomId(@PathVariable Long roomId) {
        return roomRealService.findAllRoomRealsByRoomId(roomId);
    }

    @PostMapping("/findAvailableRoomReal")
    public List<RoomRealReqDTO> findRoomRealByCheckInAndCheckOut(@RequestBody RoomRealFindForCheckInAndCheckOutReqDTO roomRealFindForCheckInAndCheckOutReqDTO,
                                                                 @RequestParam(required = false) Long roomId) {
        LocalDateTime selectFirstDay = roomRealFindForCheckInAndCheckOutReqDTO.getSelectFirstDay();
        LocalDateTime selectLastDay = roomRealFindForCheckInAndCheckOutReqDTO.getSelectLastDay();
        return roomRealService.findRoomRealByCheckInAndCheckOut(selectFirstDay, selectLastDay, roomId);
    }
}
