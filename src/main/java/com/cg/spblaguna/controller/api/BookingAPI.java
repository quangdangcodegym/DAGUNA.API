package com.cg.spblaguna.controller.api;

import com.cg.spblaguna.model.dto.req.*;
import com.cg.spblaguna.model.dto.res.BookingDetailResDTO;
import com.cg.spblaguna.model.dto.res.BookingResDTO;
import com.cg.spblaguna.model.enumeration.ERoomType;
import com.cg.spblaguna.service.booking.IBookingService;
import com.cg.spblaguna.service.cardpayment.ICardPaymentService;
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

    @Autowired
    private ICardPaymentService cardPayment;

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

    @PostMapping()
    public ResponseEntity<?> createBooking(@RequestBody BookingReqCreDTO bookingReqCreDTO) {
        BookingResDTO bookingResDTO = bookingService.saveBooking(bookingReqCreDTO);
        return new ResponseEntity<>(bookingResDTO, HttpStatus.OK);
    }

    @PatchMapping("/rooms/add")
    public ResponseEntity<?> addRoomBookingService(@RequestBody BookingReqUpdate_RoomAddDTO bookingReqUpdateRoomAddDTO) {
        BookingResDTO bookingResDTO = bookingService.saveBookingReqUpdate_RoomAddDTO(bookingReqUpdateRoomAddDTO);
        return new ResponseEntity<>(bookingResDTO, HttpStatus.OK);
    }

    @PatchMapping("/rooms/edit")
    public ResponseEntity<?> editRoomBookingService(@RequestBody BookingReqUpdate_RoomAddDTO bookingReqUpdateRoomAddDTO) {
        BookingResDTO bookingResDTO = bookingService.saveBookingReqUpdate_RoomEditDTO(bookingReqUpdateRoomAddDTO);
        return new ResponseEntity<>(bookingResDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{bookingId}/rooms/{roomId}/delete")
    public ResponseEntity<?> updateBooking_DeleteRoom(@PathVariable Long bookingId, @PathVariable Long roomId) {
        BookingResDTO bookingResDTO = bookingService.saveBookingReqUpdate_RoomDeleteDTO(bookingId, roomId);
        return new ResponseEntity<>(bookingResDTO, HttpStatus.OK);
    }

    @PatchMapping("/{bookingId}/customer")
    public ResponseEntity<?> updateBooking_AddCustomer(@PathVariable Long bookingId, @RequestBody BookingReqUpdate_CustomerDTO bookingReqUpdateCustomerDTO) {
        bookingReqUpdateCustomerDTO.setBookingId(bookingId);
        BookingResDTO bookingResDTO = bookingService.updateBooking_AddCustomer(bookingReqUpdateCustomerDTO);
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

    @PatchMapping("/{bookingId}/complete")
    public ResponseEntity<?> updateBooking_Complete(@PathVariable Long bookingId) {
        bookingService.updateBooking_Complete(bookingId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
