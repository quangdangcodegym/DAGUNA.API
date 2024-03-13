package com.cg.spblaguna.service.booking_service;

import com.cg.spblaguna.model.BookingService;
import com.cg.spblaguna.model.dto.res.BookingServiceResDTO;
import com.cg.spblaguna.service.IGeneralService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBookingService extends IGeneralService<BookingService, Long> {
    List<BookingServiceResDTO> findAllBookingServiceResDTO();
}
