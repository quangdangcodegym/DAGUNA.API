package com.cg.spblaguna.controller.api;

import com.cg.spblaguna.model.dto.res.EDepositedStatusResDTO;
import com.cg.spblaguna.model.enumeration.EDepositedStatus;
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
@RequestMapping("/api/edeposited-status")
@CrossOrigin(origins = "*")
public class EDepositedStatusAPI {
    @GetMapping
    public ResponseEntity<?> getAllEDepositedStatuses() {
        EDepositedStatus[] eDepositedStatuses = EDepositedStatus.values();
        List<EDepositedStatusResDTO> eDepositedStatusResDTOS = Arrays.stream(eDepositedStatuses)
                .map(eDepositedStatus -> new EDepositedStatusResDTO(eDepositedStatus.toString(), eDepositedStatus.getName()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(eDepositedStatusResDTOS, HttpStatus.OK);
    }
}
