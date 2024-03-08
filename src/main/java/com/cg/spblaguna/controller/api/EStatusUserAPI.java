package com.cg.spblaguna.controller.api;

import com.cg.spblaguna.model.dto.res.EStatusResDTO;
import com.cg.spblaguna.model.dto.res.EStatusUserResDTO;
import com.cg.spblaguna.model.enumeration.EStatusRoom;
import com.cg.spblaguna.model.enumeration.EStatusUser;
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
@RequestMapping("/api/estatususers")
@CrossOrigin(origins = "*")
public class EStatusUserAPI {
    @GetMapping
    public ResponseEntity<?> getAllEStatusUsers() {
        EStatusUser[] eStatusUsers = EStatusUser.values();
        List<EStatusUserResDTO> eStatusUserResDTOS = Arrays.stream(eStatusUsers)
                .map(eStatusUser -> new EStatusUserResDTO(eStatusUser.toString(), eStatusUser.getName()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(eStatusUserResDTOS, HttpStatus.OK);
    }
}
