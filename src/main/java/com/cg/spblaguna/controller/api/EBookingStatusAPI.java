package com.cg.spblaguna.controller.api;

import com.cg.spblaguna.model.dto.res.EBookStatusResDTO;
import com.cg.spblaguna.model.enumeration.EBookingStatus;
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
@RequestMapping("/api/ebooking-status")
@CrossOrigin(origins = "*")
public class EBookingStatusAPI {
    @GetMapping
    public ResponseEntity<?> getAllEBookStatuses() {
        EBookingStatus[] eBookStatuses = EBookingStatus.values();
        List<EBookStatusResDTO> eBookStatusResDTOS = Arrays.stream(eBookStatuses)
                .map(eBookStatus -> new EBookStatusResDTO(eBookStatus.toString(), eBookStatus.getName()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(eBookStatusResDTOS, HttpStatus.OK);
    }
}
