package com.cg.spblaguna.repository;

import com.cg.spblaguna.model.BookingService;
import com.cg.spblaguna.model.dto.res.BookingServiceResDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IBookingServiceRepository extends JpaRepository<BookingService, Long> {
    @Query("select new com.cg.spblaguna.model.dto.res.BookingServiceResDTO(bs) " +
            "from BookingService bs ")
    List<BookingServiceResDTO> findAllBookingServiceResDTO();
}
