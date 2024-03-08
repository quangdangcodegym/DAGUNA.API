package com.cg.spblaguna.model.dto.req;

import com.cg.spblaguna.model.enumeration.EStatusUser;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
@Getter
@Setter
public class CustomerReqDTO {
    private LocalDate dob;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private EStatusUser statusUser;
    private String password;
}
