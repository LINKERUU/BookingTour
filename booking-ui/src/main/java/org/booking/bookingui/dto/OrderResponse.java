package org.booking.bookingui.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponse(
        String id,
        String userId,
        String flightId,
        String hotelId,
        String status,
        BigDecimal amount,
        LocalDateTime createdAt,
        String reason
) {
}