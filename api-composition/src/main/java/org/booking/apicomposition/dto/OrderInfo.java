package org.booking.apicomposition.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderInfo(
        String id,
        String userId,
        String flightId,
        String hotelId,
        String status,
        BigDecimal amount,
        LocalDateTime createdAt

) {
}
