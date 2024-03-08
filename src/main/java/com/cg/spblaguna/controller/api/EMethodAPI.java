package com.cg.spblaguna.controller.api;

import com.cg.spblaguna.model.dto.res.EMethodResDTO;
import com.cg.spblaguna.model.dto.res.ERoomResDTO;
import com.cg.spblaguna.model.enumeration.EMethod;
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
@RequestMapping("/api/emethods")
@CrossOrigin(origins = "*")
public class EMethodAPI {
    @GetMapping
    public ResponseEntity<?> getAllEMethods() {
        EMethod[] eMethods = EMethod.values();
        List<EMethodResDTO> eMethodResDTOS = Arrays.stream(eMethods)
                .map(eMethod -> new EMethodResDTO(eMethod.toString(), eMethod.getName()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(eMethodResDTOS, HttpStatus.OK);
    }
}
