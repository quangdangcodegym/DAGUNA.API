package com.cg.spblaguna.model;

import com.cg.spblaguna.model.enumeration.ECardType;
import com.cg.spblaguna.model.enumeration.EMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;
    @Enumerated(EnumType.STRING)
    private EMethod method;
    private BigDecimal total;
    @Column(name = "transfer_id")

    private Long transferId;

}
