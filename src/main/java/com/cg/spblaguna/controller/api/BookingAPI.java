package com.cg.spblaguna.controller.api;

import com.cg.spblaguna.model.Booking;
import com.cg.spblaguna.model.dto.req.BookingReqCreDTO;
import com.cg.spblaguna.model.dto.req.BookingReqUpdate_BookingServiceCreUpdateDTO;
import com.cg.spblaguna.model.dto.req.BookingReqUpdate_RoomAddDTO;
import com.cg.spblaguna.model.dto.res.BookingResDTO;
import com.cg.spblaguna.service.booking.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingAPI {
    @Autowired
    private IBookingService bookingService;
    @GetMapping
    public ResponseEntity<?> getAllBooking() {
        List<BookingResDTO> bookingList = bookingService.findAllBookingResDTO();
        return new ResponseEntity<>(bookingList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable Long id) {
        BookingResDTO bookingResDTO = bookingService.findBookingResDTOById(id);
        if (bookingResDTO == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(bookingResDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingReqCreDTO bookingReqCreDTO) {
        BookingResDTO bookingResDTO = bookingService.saveBooking(bookingReqCreDTO);
        return new ResponseEntity<>(bookingResDTO, HttpStatus.OK);
    }
    @PatchMapping("/rooms/edit")
    public ResponseEntity<?> editBookingService(@RequestBody BookingReqUpdate_RoomAddDTO bookingReqUpdateRoomAddDTO) {
        BookingResDTO bookingResDTO = bookingService.saveBookingReqUpdate_RoomAddDTO(bookingReqUpdateRoomAddDTO);
        return new ResponseEntity<>(bookingResDTO, HttpStatus.OK);
    }
    @PatchMapping("/booking-services/add")
    public ResponseEntity<?> addBookingService(@RequestBody BookingReqUpdate_BookingServiceCreUpdateDTO bookingReqUpdateBookingServiceCreUpdateDTO) {
        BookingResDTO bookingResDTO = bookingService.saveBookingReqUpdate_BookingServiceAddDTO(bookingReqUpdateBookingServiceCreUpdateDTO);
        return new ResponseEntity<>(bookingResDTO, HttpStatus.OK);
    }
    @PatchMapping("/booking-services/edit")
    public ResponseEntity<?> editBookingService(@RequestBody BookingReqUpdate_BookingServiceCreUpdateDTO bookingReqUpdateBookingServiceCreUpdateDTO) {
        BookingResDTO bookingResDTO = bookingService.editBookingReqUpdate_BookingServiceEditDTO(bookingReqUpdateBookingServiceCreUpdateDTO);
        return new ResponseEntity<>(bookingResDTO, HttpStatus.OK);
    }



}
