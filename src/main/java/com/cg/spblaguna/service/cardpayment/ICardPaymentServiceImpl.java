package com.cg.spblaguna.service.cardpayment;

import com.cg.spblaguna.model.CardPayment;
import com.cg.spblaguna.model.User;
import com.cg.spblaguna.model.enumeration.ECardType;
import com.cg.spblaguna.repository.ICardPaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class ICardPaymentServiceImpl implements ICardPaymentService {
    @Autowired
    private ICardPaymentRepository cardPaymentRepository;

    @Override
    public List<CardPayment> findAll() {
        return null;
    }

    @Override
    public Optional<CardPayment> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public CardPayment save(CardPayment cardPayment) {
        return cardPaymentRepository.save(cardPayment);
    }


    @Override
    public void deleteById(Long id) {

    }

    @Override
    public CardPayment findCardPaymentByEmailAndPhone(ECardType cardType, String phone) {
        return cardPaymentRepository.findCardPaymentByEmailAndPhone(cardType, phone);
    }
}
