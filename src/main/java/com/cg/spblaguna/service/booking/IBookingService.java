package com.cg.spblaguna.service.booking;

import com.cg.spblaguna.model.Booking;
import com.cg.spblaguna.model.dto.req.BookingReqCreDTO;
import com.cg.spblaguna.model.dto.req.BookingReqUpdate_BookingServiceCreUpdateDTO;
import com.cg.spblaguna.model.dto.req.BookingReqUpdate_RoomAddDTO;
import com.cg.spblaguna.model.dto.res.BookingResDTO;
import com.cg.spblaguna.service.IGeneralService;

import java.util.List;

public interface IBookingService extends IGeneralService<Booking,Long> {
    List<Booking> findAllByCustomerId(Long customerId);
    List<Booking>findByRoomIdAndBookingDate(Long roomId, String bookingDate);

    BookingResDTO saveBooking(BookingReqCreDTO bookingReqCreDTO);

    BookingResDTO saveBookingReqUpdate_BookingServiceAddDTO(BookingReqUpdate_BookingServiceCreUpdateDTO bookingReqUpdateBookingServiceCreUpdateDTO);

    BookingResDTO editBookingReqUpdate_BookingServiceEditDTO(BookingReqUpdate_BookingServiceCreUpdateDTO bookingReqUpdateBookingServiceCreUpdateDTO);

    BookingResDTO saveBookingReqUpdate_RoomAddDTO(BookingReqUpdate_RoomAddDTO bookingReqUpdateRoomAddDTO);
}
