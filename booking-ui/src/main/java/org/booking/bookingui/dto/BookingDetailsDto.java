package org.booking.bookingui.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BookingDetailsDto(
        String orderId,
        String status,
        BigDecimal totalAmount,
        LocalDateTime createdAt,
        FlightDto flight,
        HotelDto hotel,
        PaymentDto payment
) {
}