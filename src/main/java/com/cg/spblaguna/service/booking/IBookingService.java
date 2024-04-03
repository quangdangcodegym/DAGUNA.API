package com.cg.spblaguna.service.booking;

import com.cg.spblaguna.model.Booking;
import com.cg.spblaguna.model.dto.req.*;
import com.cg.spblaguna.model.dto.res.BookingResDTO;
import com.cg.spblaguna.service.IGeneralService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface IBookingService extends IGeneralService<Booking,Long> {
    List<BookingResDTO> findAllBookingResDTO();
    BookingResDTO findBookingResDTOById(Long id);

    List<Booking> findAllByCustomerId(Long customerId);
    List<Booking>findByRoomIdAndBookingDate(Long roomId, String bookingDate);

    BookingResDTO saveBooking(BookingReqCreDTO bookingReqCreDTO);

    BookingResDTO saveBookingReqUpdate_BookingServiceAddDTO(BookingReqUpdate_BookingServiceCreUpdateDTO bookingReqUpdateBookingServiceCreUpdateDTO);

    BookingResDTO editBookingReqUpdate_BookingServiceEditDTO(BookingReqUpdate_BookingServiceCreUpdateDTO bookingReqUpdateBookingServiceCreUpdateDTO);

    BookingResDTO saveBookingReqUpdate_RoomAddDTO(BookingReqUpdate_RoomAddDTO bookingReqUpdateRoomAddDTO);

    BookingResDTO saveBookingReqUpdate_RoomEditDTO(BookingReqUpdate_RoomAddDTO bookingReqUpdateRoomAddDTO);

    BookingResDTO saveBookingReqUpdate_RoomDeleteDTO(Long bookingId , Long roomId);

    void updateBooking_Complete(Long bookingId);

    BookingResDTO updateBooking_AddCustomer(BookingReqUpdate_CustomerDTO bookingReqUpdateCustomerDTO);


    void updateBooking_UpdateBookingDetail_UpdateRoomReal(Long bookingDetailId, Long roomRealId);

    void depositBooking(DepositReqDTO depositReqDTO);
    RevenueReqDTO findRevenueForByTime(LocalDateTime selectFirstDay, LocalDateTime selectLastDay);
}
