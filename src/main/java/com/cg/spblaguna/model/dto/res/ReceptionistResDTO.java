package com.cg.spblaguna.model.dto.res;


import com.cg.spblaguna.model.Image;
import com.cg.spblaguna.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
    private String address;
    private LocalDate createAt;
    private String avatarImgResDTO;
    private String receptionistInfo;

    public ReceptionistResDTO(Long id, String receptionistName, LocalDate dob, String email, String phone, String address, LocalDate createAt, String receptionistInfo) {
        this.id = id;
        this.receptionistName = receptionistName;
        this.dob = dob;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.createAt = createAt;
        this.receptionistInfo = receptionistInfo;
    }

    public ReceptionistResDTO(User user){
        this.setId(user.getId());
        this.setReceptionistName(user.getReceptionistName());
        this.setDob(user.getDob());
        this.setEmail(user.getEmail());
        this.setPhone(user.getPhone());
        this.setAddress(user.getAddress());

        if (!user.getUserImages().isEmpty()) {
            this.setAvatarImgResDTO(user.getUserImages().get(0).getFileUrl());
        } else {
            this.setAvatarImgResDTO("https://bit.ly/499RjtL");
        }
        this.setReceptionistInfo(user.getReceptionistInfo());
    }



}
