package com.cg.spblaguna.model.dto.res;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ReceptionistResDTO {
    private Long id;
    private String receptionistName;
    private LocalDate dob;
    private String email;
    private String phone;
    private LocalDate createAt;
    private String avatarImg;
    private String receptionistInfo;
}
