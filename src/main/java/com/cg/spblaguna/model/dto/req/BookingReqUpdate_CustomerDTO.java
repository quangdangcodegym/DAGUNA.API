package com.cg.spblaguna.model.dto.req;

import com.cg.spblaguna.model.enumeration.ECardType;
import com.cg.spblaguna.model.enumeration.EPrefix;
import com.cg.spblaguna.model.enumeration.EStatusUser;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class BookingReqUpdate_CustomerDTO {
    private EPrefix ePrefix;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String country;
    private String zipCode;
    private String email;
    private ECardType cardType;
    private String cardNumber;
    private LocalDate expirationDate;
    private String cvv;
    private String nameCard;
    private Long bookingId;

}
