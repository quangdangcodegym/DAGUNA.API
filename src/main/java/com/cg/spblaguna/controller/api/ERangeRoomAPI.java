package com.cg.spblaguna.controller.api;

import com.cg.spblaguna.model.dto.res.ERangeRoomResDTO;
import com.cg.spblaguna.model.enumeration.ERangeRoom;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/erange-rooms")
@CrossOrigin(origins = "*")
public class ERangeRoomAPI {
    @GetMapping
    public ResponseEntity<?> getAllERangeRooms() {
        ERangeRoom[] eRangeRooms = ERangeRoom.values();
        List<ERangeRoomResDTO> eRangeRoomResDTOS = Arrays.stream(eRangeRooms)
                .map(eRangeRoom -> new ERangeRoomResDTO(eRangeRoom.toString(), eRangeRoom.getName()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(eRangeRoomResDTOS, HttpStatus.OK);
    }
}
