package com.cg.spblaguna.repository;

import com.cg.spblaguna.model.BookingDetail;
import com.cg.spblaguna.model.dto.req.RevenueReqDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface IBookingDetailRepository extends JpaRepository<BookingDetail, Long> {
    List<BookingDetail> findBookingDetailsByBooking_Id(Long booking_id);

    @Query(value = "SELECT new com.cg.spblaguna.model.dto.req.RevenueReqDTO(Sum(b.total)) " +
            " FROM Booking b " +
            "WHERE" +
            " (b.bookingAt BETWEEN :selectFirstDay  AND  :selectLastDay) " +
            "AND b.bookingStatus = 'PAID' ")
    RevenueReqDTO findRevenueForByTime(@Param("selectFirstDay") LocalDateTime selectFirstDay, @Param("selectLastDay") LocalDateTime selectLastDay);
}
