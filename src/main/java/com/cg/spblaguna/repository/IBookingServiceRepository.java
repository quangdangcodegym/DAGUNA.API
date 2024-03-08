package com.cg.spblaguna.repository;

import com.cg.spblaguna.model.BookingService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBookingServiceRepository extends JpaRepository<BookingService, Long> {
}
