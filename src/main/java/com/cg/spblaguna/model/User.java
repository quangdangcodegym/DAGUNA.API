package com.cg.spblaguna.model;

import com.cg.spblaguna.model.dto.res.CustomerInfoResDTO;
import com.cg.spblaguna.model.dto.res.ImageResDTO;
import com.cg.spblaguna.model.dto.res.ReceptionistResDTO;
import com.cg.spblaguna.model.enumeration.EBookingStatus;
import com.cg.spblaguna.model.enumeration.ELockStatus;
import com.cg.spblaguna.model.enumeration.EPrefix;
import com.cg.spblaguna.model.enumeration.ERole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
@Accessors(chain = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dob;
    @Column(unique = true, nullable = false)
    private String email;

    private String password;
//    @NotEmpty(message = "Tên lễ tân không được trống")
    @Column(name = "receptionist_name")
    private String receptionistName;


    @Column(name = "prefix")
    @Enumerated(EnumType.STRING)
    private EPrefix ePrefix;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String phone;

    private String address;


    private String country;

    @Column(name = "zip_code")
    private String zipCode;
    private String code;

    @Column(name = "create_at")
    private LocalDate createAt;

    @Column(name = "deleted", columnDefinition = "boolean default false")
    private Boolean deleted;




    @Column(name = "receptionist_info", columnDefinition = "LONGTEXT")
    private String receptionistInfo;

    @OneToOne
    @JoinColumn(name = "cardpayment_id", nullable = true)
    private CardPayment cardPayment;


//    @Enumerated(EnumType.STRING)
//    private EBookingStatus eBookStatubookStatus;

    @OneToMany(mappedBy = "user")
    private List<Image> userImages;


    @Column(name="un_lock")
    private boolean unlock = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_receptionist")
    private ELockStatus eLockStatus;

    @Enumerated(EnumType.STRING)
    private ERole eRole;


    @OneToMany(mappedBy = "user")
    private List<Booking> bookings;


    public ReceptionistResDTO toReceptionistResDTO(){
        ReceptionistResDTO receptionistResDTO = new ReceptionistResDTO();
        receptionistResDTO.setId(id);
        receptionistResDTO.setReceptionistName(receptionistName);
        receptionistResDTO.setELockStatus(eLockStatus);
        receptionistResDTO.setDob(dob);
        receptionistResDTO.setEmail(email);
        receptionistResDTO.setPhone(phone);
        receptionistResDTO.setAddress(address);
        receptionistResDTO.setReceptionistInfo(receptionistInfo);
        List<ImageResDTO> imageResDTOS = this.getUserImages()
                .stream()
                .map(m -> {
                    ImageResDTO imageResDTO = new ImageResDTO();
                    imageResDTO.setId(m.getId());
                    imageResDTO.setFileUrl(m.getFileUrl());
                    return imageResDTO;
                })
                .collect(Collectors.toList());
        if (imageResDTOS.size() > 0) {
            receptionistResDTO.setAvatarImgResDTO(imageResDTOS.get(0).getFileUrl());
        }
        return receptionistResDTO;
    }


    public CustomerInfoResDTO toCustomerInfoResDTO(){

        CustomerInfoResDTO customerInfoResDTO = new CustomerInfoResDTO();

        customerInfoResDTO.setEPrefix(this.getEPrefix());
        customerInfoResDTO.setFirstName(this.getFirstName());
        customerInfoResDTO.setLastName(this.getLastName());
        customerInfoResDTO.setPhone(this.getPhone());
        customerInfoResDTO.setAddress(this.getAddress());
        customerInfoResDTO.setCountry(this.getCountry());
        customerInfoResDTO.setEmail(this.getEmail());
        customerInfoResDTO.setNameCard(this.getCardPayment().getNameCard());
        customerInfoResDTO.setCardType(this.getCardPayment().getCardType());
        customerInfoResDTO.setNameCard(this.getCardPayment().getNameCard());
        customerInfoResDTO.setExpirationDate(this.getCardPayment().getExpirationDate());
        customerInfoResDTO.setCvv(this.getCardPayment().getCvv());
        return null;
    }


}
