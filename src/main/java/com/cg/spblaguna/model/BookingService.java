package com.cg.spblaguna.model;

import com.cg.spblaguna.model.dto.res.BookingServiceResDTO;
import com.cg.spblaguna.model.enumeration.EBookingServiceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "booking_services")
public class BookingService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private BigDecimal price;

    @Column(name = "file_url")
    private String fileUrl;

    @Enumerated(EnumType.STRING)
    private EBookingServiceType bookingServiceType;

    public BookingServiceResDTO toBookingServiceDTO(){
        return new BookingServiceResDTO(this.id, this.name, this.description, this.price, this.bookingServiceType, this.fileUrl);
    }
}
