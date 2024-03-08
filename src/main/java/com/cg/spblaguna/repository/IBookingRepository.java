package com.cg.spblaguna.repository;

import com.cg.spblaguna.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
public interface IBookingRepository extends JpaRepository<Booking,Long> {


}
