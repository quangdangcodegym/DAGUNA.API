package com.cg.spblaguna.controller.api;

import com.cg.spblaguna.model.dto.res.EImageTypeResDTO;
import com.cg.spblaguna.model.dto.res.ELockResDTO;
import com.cg.spblaguna.model.enumeration.EImageType;
import com.cg.spblaguna.model.enumeration.ELockStatus;
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
@RequestMapping("/api/eimage-types")
@CrossOrigin(origins = "*")
public class EImageTypeAPI {
    @GetMapping
    public ResponseEntity<?> getAllEImageTypes() {
        EImageType[] eImageTypes = EImageType.values();
        List<EImageTypeResDTO> eImageTypeResDTOS = Arrays.stream(eImageTypes)
                .map(eImageType -> new EImageTypeResDTO(eImageType.toString(), eImageType.getName()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(eImageTypeResDTOS, HttpStatus.OK);
    }
}
