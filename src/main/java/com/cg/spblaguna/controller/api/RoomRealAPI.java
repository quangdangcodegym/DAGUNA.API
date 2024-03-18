package com.cg.spblaguna.controller.api;

import com.cg.spblaguna.model.PerType;
import com.cg.spblaguna.model.RoomReal;
import com.cg.spblaguna.model.dto.req.ReceptionistReqDTO;
import com.cg.spblaguna.model.dto.req.RoomRealReqDTO;
import com.cg.spblaguna.repository.IRoomRealRepository;
import com.cg.spblaguna.service.roomreal.IRoomRealService;
import com.cg.spblaguna.service.roomreal.RoomRealServiceImpl;
import com.cg.spblaguna.util.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getAllRoomReal(){
        List<RoomReal> roomReals= roomRealService.findAll();
        return new ResponseEntity<>(roomReals, HttpStatus.OK);
    }
    @GetMapping("/{roomId}")
    public List<RoomReal> findByRoomId(@PathVariable Long roomId) {
        return roomRealService.findAllRoomRealsByRoomId(roomId);
    }
}
