package com.cg.spblaguna.service.payment;

import com.cg.spblaguna.model.Payment;

import java.util.List;
import java.util.Optional;

public class PaymentService implements IPaymentService {
    @Override
    public List<Payment> findAll() {
        return null;
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Payment save(Payment payment) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
