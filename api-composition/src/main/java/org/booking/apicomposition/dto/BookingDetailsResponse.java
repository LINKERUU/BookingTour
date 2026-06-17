package org.booking.apicomposition.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BookingDetailsResponse(
        String orderId,
        String status,
        BigDecimal totalAmount,
        LocalDateTime createdAt,
        FlightInfo flight,
        HotelInfo hotel,
        PaymentInfo payment
) {
}
