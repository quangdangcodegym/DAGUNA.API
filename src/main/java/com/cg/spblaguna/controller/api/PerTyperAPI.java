package com.cg.spblaguna.controller.api;

import com.cg.spblaguna.model.PerType;
import com.cg.spblaguna.service.pertype.IPerTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pertype")
@CrossOrigin(origins = "*")
public class PerTyperAPI {
    @Autowired
    private IPerTypeService perTypeService;

    @GetMapping
    public ResponseEntity<?> getAllPerType(){
        List<PerType> perTypes= perTypeService.findAll();
        return new ResponseEntity<>(perTypes, HttpStatus.OK);
    }
}
