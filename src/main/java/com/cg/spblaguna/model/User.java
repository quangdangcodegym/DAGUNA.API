package com.cg.spblaguna.model;

import com.cg.spblaguna.model.enumeration.EBookingStatus;
import com.cg.spblaguna.model.enumeration.ERole;
import jakarta.persistence.*;
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

    @Column(name = "create_at")
    private LocalDate createAt;

    /**
     * total is full booking_details
     */
    private BigDecimal total;


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
    private ERole eRole;






}
