package com.cg.spblaguna.controller.api;

import com.cg.spblaguna.model.dto.res.BookingResDTO;
import com.cg.spblaguna.model.dto.res.CustomerResDTO;
import com.cg.spblaguna.service.receptionist.ReceptionistServiceImpl;
import com.cg.spblaguna.service.room.RoomServiceImpl;
import com.cg.spblaguna.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserAPI {
    @Autowired
    private ReceptionistServiceImpl receptionistService;

    @Autowired
    private RoomServiceImpl roomService;

    @Autowired
    private IUserService userService;





}
