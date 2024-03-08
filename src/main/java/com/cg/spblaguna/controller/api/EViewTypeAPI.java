package com.cg.spblaguna.controller.api;

import com.cg.spblaguna.model.dto.res.EStatusUserResDTO;
import com.cg.spblaguna.model.dto.res.EViewTypeResDTO;
import com.cg.spblaguna.model.enumeration.EStatusUser;
import com.cg.spblaguna.model.enumeration.EViewType;
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
@RequestMapping("/api/eview-types")
@CrossOrigin(origins = "*")
public class EViewTypeAPI {
    @GetMapping
    public ResponseEntity<?> getAllEViewTypers() {
        EViewType[] eViewTypes = EViewType.values();
        List<EViewTypeResDTO> eViewTypeResDTOS = Arrays.stream(eViewTypes)
                .map(eViewType -> new EViewTypeResDTO(eViewType.toString(), eViewType.getName()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(eViewTypeResDTOS, HttpStatus.OK);
    }
}
