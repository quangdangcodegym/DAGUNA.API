package com.cg.spblaguna.controller.api;

import com.cg.spblaguna.model.KindOfRoom;
import com.cg.spblaguna.service.kindofroom.IKindOfRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/kindofroom")
@CrossOrigin(origins = "*")
public class KindOfRoomAPI {
    @Autowired
    private IKindOfRoomService kindOfRoomService;

    @GetMapping
    public ResponseEntity<?> getAllKindOfRoom(){
        List<KindOfRoom> kindOfRooms = kindOfRoomService.findAll();
        return new ResponseEntity<>(kindOfRooms, HttpStatus.OK);
    }
}
