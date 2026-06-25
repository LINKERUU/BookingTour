package org.booking.orderservice.dto;

import org.booking.orderservice.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponse(
        String id,
        String userId,
        String flightId,
        String hotelId,
        BigDecimal amount,
        OrderStatus status,
        LocalDateTime createdAt,
        String reason
) {
}
