package com.cg.spblaguna.repository;

import com.cg.spblaguna.model.BookingDetail;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IBookingDetailRepository extends JpaRepository<BookingDetail,Long> {
    List<BookingDetail> findBookingDetailsByBooking_Id(Long booking_id);


}
