package com.cg.spblaguna.model.dto.res;

import com.cg.spblaguna.model.enumeration.EStatusUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResDTO {
    private LocalDate dob;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private EStatusUser statusUser;
}
