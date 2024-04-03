package com.cg.spblaguna.repository;

import com.cg.spblaguna.model.Booking;
import com.cg.spblaguna.model.dto.res.BookingResDTO;
import com.cg.spblaguna.model.report.RevenueByMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface IBookingRepository extends JpaRepository<Booking, Long> {


    @Query("select new com.cg.spblaguna.model.dto.res.BookingResDTO(b) from Booking b " +
            "where b.id = :id")
    BookingResDTO findBookingResDTOById(@Param("id") Long id);

    @Query("select new com.cg.spblaguna.model.dto.res.BookingResDTO(b) from Booking b")
    List<BookingResDTO> findAllBookingResDTO();

    @Query(value = "SELECT  " +
            "    DATE_FORMAT(DATE_SUB(CURRENT_DATE(), INTERVAL n.n MONTH), '%Y-%m') AS month_year, " +
            "    IFNULL(SUM(b.total), 0) AS total_amount " +
            "FROM  " +
            "    (SELECT 0 AS n UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10 UNION ALL SELECT 11) AS n " +
            "LEFT JOIN  " +
            "    bookings b ON DATE_FORMAT(b.booking_at, '%Y-%m') = DATE_FORMAT(DATE_SUB(CURRENT_DATE(), INTERVAL n.n MONTH), '%Y-%m')  and b.booking_status= 'PAID' " +
            "WHERE  " +
            "    DATE_SUB(CURRENT_DATE(), INTERVAL n.n MONTH) BETWEEN DATE_SUB(CURRENT_DATE(), INTERVAL 12 MONTH) AND DATE_SUB(CURRENT_DATE(), INTERVAL 1 MONTH) " +
            "    " +
            "GROUP BY  " +
            "    DATE_FORMAT(DATE_SUB(CURRENT_DATE(), INTERVAL n.n MONTH), '%Y-%m') " +
            "ORDER BY  " +
            "    month_year; ", nativeQuery = true)
    List<RevenueByMonth> showRevenue();
    
}
