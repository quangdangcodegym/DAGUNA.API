package com.cg.spblaguna.controller.api;

import com.cg.spblaguna.model.dto.res.ERoomResDTO;
import com.cg.spblaguna.model.dto.res.EStatusResDTO;
import com.cg.spblaguna.model.enumeration.ERoomType;
import com.cg.spblaguna.model.enumeration.EStatusRoom;
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
@RequestMapping("/api/estatus")
@CrossOrigin(origins = "*")
public class EStatusAPI {
    @GetMapping
    public ResponseEntity<?> getAllEStatus() {
        EStatusRoom[] eStatusRooms = EStatusRoom.values();
        List<EStatusResDTO> eStatusResDTOS = Arrays.stream(eStatusRooms)
                .map(eStatus -> new EStatusResDTO(eStatus.toString(), eStatus.getName()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(eStatusResDTOS, HttpStatus.OK);
    }
}
