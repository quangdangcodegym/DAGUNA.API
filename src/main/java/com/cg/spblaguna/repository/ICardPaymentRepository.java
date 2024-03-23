package com.cg.spblaguna.repository;

import com.cg.spblaguna.model.CardPayment;
import com.cg.spblaguna.model.enumeration.ECardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ICardPaymentRepository extends JpaRepository<CardPayment, Long> {

    @Query("select c from CardPayment c where c.cardType = :cardType and c.cardNumber like :cardNumber")
    CardPayment findCardPaymentByEmailAndPhone(@Param("cardType") ECardType cardType,@Param("cardNumber") String cardNumber);
}
