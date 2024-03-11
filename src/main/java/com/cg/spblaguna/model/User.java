package com.cg.spblaguna.model;

import com.cg.spblaguna.model.enumeration.EBookingStatus;
import com.cg.spblaguna.model.enumeration.ELockStatus;
import com.cg.spblaguna.model.enumeration.ERole;
import com.cg.spblaguna.model.enumeration.EStatusRoom;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dob;
    @Column(unique = true, nullable = false)
    private String email;
    @NotEmpty(message = "Tên lễ tân không được trống")
    @Column(name = "receptionist_name")
    private String receptionistName;
    private String phone;
    private String address;

    @Column(name = "create_at")
    private LocalDate createAt;

    @Column(name = "deleted", columnDefinition = "int default 0")
    private int deleted = 0;
    @Column(name = "receptionist_info", columnDefinition = "LONGTEXT")
    private String receptionistInfo;

    @OneToOne
    @JoinColumn(name = "cardpayment_id", nullable = true)
    private CardPayment cardPayment;


    @Enumerated(EnumType.STRING)
    private EBookingStatus eBookStatusookStatus;

    @OneToMany(mappedBy = "user")
    private List<Image> userImages;


    @Column(name="un_lock")
    private boolean unlock = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_receptionist")
    private ELockStatus eLockStatus;

    @Enumerated(EnumType.STRING)
    private ERole eRole;






}
