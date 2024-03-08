package com.cg.spblaguna.controller.api;

import com.cg.spblaguna.model.dto.res.ERoleResDTO;
import com.cg.spblaguna.model.dto.res.ERoomResDTO;
import com.cg.spblaguna.model.enumeration.ERole;
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
@RequestMapping("/api/eroles")
@CrossOrigin(origins = "*")
public class ERoleAPI {
    @GetMapping
    public ResponseEntity<?> getAllERoles() {
        ERole[] eRoles = ERole.values();
        List<ERoleResDTO> eRoleResDTOS = Arrays.stream(eRoles)
                .map(eRole -> new ERoleResDTO(eRole.toString(), eRole.getName()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(eRoleResDTOS, HttpStatus.OK);
    }
}
