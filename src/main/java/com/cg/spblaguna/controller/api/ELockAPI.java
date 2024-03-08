package com.cg.spblaguna.controller.api;

import com.cg.spblaguna.model.dto.res.ELockResDTO;
import com.cg.spblaguna.model.dto.res.EMethodResDTO;
import com.cg.spblaguna.model.enumeration.ELockStatus;
import com.cg.spblaguna.model.enumeration.EMethod;
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
@RequestMapping("/api/elocks")
@CrossOrigin(origins = "*")
public class ELockAPI {
    @GetMapping
    public ResponseEntity<?> getAllELocks() {
        ELockStatus[] eLockStatuses = ELockStatus.values();
        List<ELockResDTO> eLockResDTOS = Arrays.stream(eLockStatuses)
                .map(eLock -> new ELockResDTO(eLock.toString(), eLock.getName()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(eLockResDTOS, HttpStatus.OK);
    }
}
