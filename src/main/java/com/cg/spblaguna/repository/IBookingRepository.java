package com.cg.spblaguna.repository;

import com.cg.spblaguna.model.Booking;
import com.cg.spblaguna.model.dto.res.BookingResDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IBookingRepository extends JpaRepository<Booking,Long> {

        @Query("select new com.cg.spblaguna.model.dto.res.BookingResDTO(b) from Booking b " +
                "where b.id = :id")
        BookingResDTO findBookingResDTOById(@Param("id") Long id);
        @Query("select new com.cg.spblaguna.model.dto.res.BookingResDTO(b) from Booking b")
        List<BookingResDTO> findAllBookingResDTO();

}
