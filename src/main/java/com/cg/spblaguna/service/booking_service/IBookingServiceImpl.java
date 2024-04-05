package com.cg.spblaguna.service.booking_service;

import com.cg.spblaguna.model.BookingService;
import com.cg.spblaguna.model.dto.res.BookingServiceResDTO;
import com.cg.spblaguna.repository.IBookingServiceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class IBookingServiceImpl implements IBookingService{
    @Autowired
    private IBookingServiceRepository bookingServiceRepository;
    @Override
    public List<BookingService> findAll() {
        return bookingServiceRepository.findAll();
    }

    @Override
    public Optional<BookingService> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public BookingService save(BookingService bookingService) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<BookingServiceResDTO> findAllBookingServiceResDTO() {
        return bookingServiceRepository.findAllBookingServiceResDTO();
    }
}
