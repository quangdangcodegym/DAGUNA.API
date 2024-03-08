package com.cg.spblaguna.model;

import com.cg.spblaguna.model.enumeration.EMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Entity

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    private Long amount;

    private Long vat;

    private Timestamp date;
    @Enumerated(EnumType.STRING)

    private EMethod method;

    private Long total;

    private Long transfer;

    @Column(columnDefinition = "LONGTEXT")
    private String fileName;
    @Column(columnDefinition = "LONGTEXT")
    private String fileType;
    @Lob
    @Column(name = "file", columnDefinition = "LONGBLOB")
    private byte[] file;
    @Column(name = "note", columnDefinition = "LONGTEXT")
    private String note;
}
