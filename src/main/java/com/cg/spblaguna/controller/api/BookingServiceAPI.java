package com.cg.spblaguna.controller.api;

import com.cg.spblaguna.model.dto.res.BookingServiceResDTO;
import com.cg.spblaguna.service.booking_service.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/booking-services")
@CrossOrigin(origins = "*")
public class BookingServiceAPI {
    @Autowired
    private IBookingService bookingService;
    @GetMapping
    public ResponseEntity<?> getAllBookingService() {
        List<BookingServiceResDTO> bookingList = bookingService.findAllBookingServiceResDTO();
        return new ResponseEntity<>(bookingList, HttpStatus.OK);
    }
}
