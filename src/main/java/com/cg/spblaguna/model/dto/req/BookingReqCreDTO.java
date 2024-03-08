package com.cg.spblaguna.model.dto.req;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * Được sử dụng khi lần đầu tạo mới 1 booking: chỉ cần đưa lên 1 bookingDetail đầu tiên
 */
@Getter
@Setter
public class BookingReqCreDTO {
    private BookingDetailDTO bookingDetail;
}
