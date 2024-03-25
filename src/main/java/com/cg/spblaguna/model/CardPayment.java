package com.cg.spblaguna.model;

import com.cg.spblaguna.model.enumeration.ECardType;
import com.cg.spblaguna.model.enumeration.EPrefix;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "card_payments")
public class CardPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;




    @Column(name = "card_type")
    @Enumerated(EnumType.STRING)
    private ECardType cardType;


    @Column(name = "card_number")
    private String cardNumber;
    @Column(name = "expiration_date")
    private LocalDate expirationDate;
    private String cvv;
    @Column(name = "name_card")
    private String nameCard;

//    private Timestamp date;



}