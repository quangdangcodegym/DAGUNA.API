package com.cg.spblaguna.repository;

import com.cg.spblaguna.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPaymentRepository extends JpaRepository<Payment, Long> {
}
