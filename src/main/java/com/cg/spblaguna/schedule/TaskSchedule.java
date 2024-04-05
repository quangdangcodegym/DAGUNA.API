package com.cg.spblaguna.schedule;

import com.cg.spblaguna.model.Booking;
import com.cg.spblaguna.model.BookingDetail;
import com.cg.spblaguna.service.booking.IBookingService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Transactional
public class TaskSchedule {
    @Autowired
    private IBookingService bookingService;
    @Scheduled(fixedRate = 60000) // Gửi email mỗi 1 phút
    public void sendEmailTask() {
        System.out.println("AAAAAAAAAA");
        List<Booking> bookings = bookingService.findAll();
        LocalDateTime currentDateTime = LocalDateTime.now();

        for (Booking booking : bookings) {
            for (BookingDetail bookingDetail : booking.getBookingDetails()) {
                LocalDateTime checkInDateTime = bookingDetail.getCheckIn();
                if (currentDateTime.minusDays(1).isBefore(checkInDateTime)) {
                    System.out.println("Ngày hiện tại cách ngày check-in trước 1 ngày.");
                } else {
                    System.out.println("Ngày hiện tại không cách ngày check-in trước 1 ngày.");
                }
            }
        }
    }
}
