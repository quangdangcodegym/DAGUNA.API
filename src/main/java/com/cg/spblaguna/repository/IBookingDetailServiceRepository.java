package com.cg.spblaguna.repository;

import com.cg.spblaguna.model.BookingDetailService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBookingDetailServiceRepository extends JpaRepository<BookingDetailService, Long> {

    List<BookingDetailService> findBookingDetailServiceByBookingDetail_Id(Long bookingDetail_id);

    BookingDetailService findBookingDetailServiceByBookingDetail_IdAndBookingService_Id(Long bookingDetail_id, Long bookingService_id);
}
