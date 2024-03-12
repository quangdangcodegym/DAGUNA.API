package com.cg.spblaguna.controller.api;

import com.cg.spblaguna.model.Utilitie;
import com.cg.spblaguna.service.utility.IUtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/utility")
@CrossOrigin(origins ="*")
public class UtilityAPI {
    @Autowired
    private IUtilityService utilityService;

    @GetMapping
    public ResponseEntity<?> getAllUtility(){
        List<Utilitie> utilities = utilityService.findAll();
        return new ResponseEntity<>(utilities, HttpStatus.OK);
    }
}
