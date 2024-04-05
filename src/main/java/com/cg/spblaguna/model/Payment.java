package com.cg.spblaguna.model;

import com.cg.spblaguna.model.enumeration.EBank;
import com.cg.spblaguna.model.enumeration.EMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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

    @Enumerated(EnumType.STRING)
    private EBank bank;

    private String note;

    @Column(name = "transfer_date")
    private LocalDate transferDate;
}
