package com.cg.spblaguna.service.cardpayment;

import com.cg.spblaguna.model.CardPayment;
import com.cg.spblaguna.model.enumeration.ECardType;
import com.cg.spblaguna.service.IGeneralService;

public interface ICardPaymentService extends IGeneralService<CardPayment, Long> {
    CardPayment findCardPaymentByEmailAndPhone(ECardType cardType, String phone);
}
