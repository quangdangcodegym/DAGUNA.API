package com.cg.spblaguna.model;

import com.cg.spblaguna.model.dto.res.BookingDetailResDTO;
import com.cg.spblaguna.model.enumeration.EBookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "booking_details")
public class BookingDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Column(name = "checkin")
    private LocalDateTime checkIn;
    @Column(name = "checkout")
    private LocalDateTime checkOut;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "deleted", columnDefinition = "boolean default false")
    private Boolean deleted;

    /**
     * total: total của các services + VAT + total_amount
     */
    private BigDecimal total;

    private BigDecimal vat;

    @Column(name = "number_adult")
    private Integer numberAdult;
    @Column(name = "number_children")
    private Integer numberChildren;
    @Column(name = "discount_code")
    private String discountCode;


    public BookingDetailResDTO toBookingDetailResDTO(){
        BookingDetailResDTO bookingDetailResDTO = new BookingDetailResDTO();
        bookingDetailResDTO.setRoom(this.getRoom().toRoomResDto());
        bookingDetailResDTO.setCheckOut(this.getCheckOut());
        bookingDetailResDTO.setCheckIn(this.getCheckIn());
        bookingDetailResDTO.setNumberAdult(this.getNumberAdult());
        bookingDetailResDTO.setNumberChildren(this.getNumberChildren());
        bookingDetailResDTO.setTotalAmount(this.getTotalAmount());
        bookingDetailResDTO.setTotal(this.getTotal());
        bookingDetailResDTO.setVat(this.getVat());
        bookingDetailResDTO.setDiscountCode(this.getDiscountCode());
        return bookingDetailResDTO;
    }

}
