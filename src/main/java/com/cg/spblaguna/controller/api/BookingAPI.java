package com.cg.spblaguna.controller.api;

import com.cg.spblaguna.model.dto.req.*;
import com.cg.spblaguna.model.dto.res.BookingResDTO;
import com.cg.spblaguna.service.booking.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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

    @GetMapping("/show-revenue-by-month")
    public ResponseEntity<?> showRevenue() {
//        List<RevenueByMonthDTO> revenueByMonthDTOS = bookingService.showRevenue()
//                .stream().map(item -> new RevenueByMonthDTO(item.getMonth_Year(), item.getTotal_Amount())).collect(Collectors.toList());
        return new ResponseEntity<>(bookingService.showRevenue(), HttpStatus.OK);
    }

    @PatchMapping("/{bookingId}/complete")
    public ResponseEntity<?> updateBooking_Complete(@PathVariable Long bookingId) {
        bookingService.updateBooking_Complete(bookingId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/update/booking-detail/{bookingDetailId}")
    public ResponseEntity<?> getUpdateBooking_UpdateBookingDetail_UpdateRoomReal(@PathVariable Long
                                                                                         bookingDetailId, @RequestParam(required = true) Long roomRealId) {
        bookingService.updateBooking_UpdateBookingDetail_UpdateRoomReal(bookingDetailId, roomRealId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody DepositReqDTO depositReqDTO) {
        bookingService.depositBooking(depositReqDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/find-revenue")
    public ResponseEntity<?> findRevenueForByTime(@RequestBody TimeFirstAndLastReqDTO timeFirstAndLastReqDTO) {
        LocalDateTime selectFirstDay = timeFirstAndLastReqDTO.getSelectFirstDay();
        LocalDateTime selectLastDay = timeFirstAndLastReqDTO.getSelectLastDay();

        RevenueReqDTO revenueReqDTOS = bookingService.findRevenueForByTime(selectFirstDay, selectLastDay);
        return new ResponseEntity<>(revenueReqDTOS, HttpStatus.OK);
    }
}
