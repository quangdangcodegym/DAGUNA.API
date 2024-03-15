package com.cg.spblaguna.controller.api;

import com.cg.spblaguna.service.receptionist.ReceptionistServiceImpl;
import com.cg.spblaguna.service.room.RoomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserAPI {
    @Autowired
    private ReceptionistServiceImpl receptionistService;

    @Autowired
    private RoomServiceImpl roomService;



}
