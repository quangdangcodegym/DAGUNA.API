package com.cg.spblaguna.model.dto.res;

import com.cg.spblaguna.model.enumeration.ECardType;
import com.cg.spblaguna.model.enumeration.EPrefix;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInfoResDTO {
//    private Long id;
    private EPrefix ePrefix;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
//    private String city;
    private String country;
    private String zipCode;
    private String email;
    private ECardType cardType;
    private String cardNumber;
    private LocalDate expirationDate;
    private String cvv;
    private String nameCard;
}
