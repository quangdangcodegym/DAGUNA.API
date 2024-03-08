package com.cg.spblaguna.controller.api;

import com.cg.spblaguna.model.Booking;
import com.cg.spblaguna.model.dto.res.ERoomResDTO;
import com.cg.spblaguna.model.enumeration.ERoomType;
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
@RequestMapping("/api/erooms")
@CrossOrigin(origins = "*")
public class ERoomAPI {
    @GetMapping
    public ResponseEntity<?> getAllERooms() {
        ERoomType [] eRoomTypes = ERoomType.values();
        List<ERoomResDTO> eRoomResDTOS = Arrays.stream(eRoomTypes)
                .map(eRoomType -> new ERoomResDTO(eRoomType.toString(), eRoomType.getName()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(eRoomResDTOS, HttpStatus.OK);
    }
}
