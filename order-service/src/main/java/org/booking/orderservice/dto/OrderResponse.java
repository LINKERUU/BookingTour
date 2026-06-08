package org.booking.orderservice.dto;

import org.booking.orderservice.model.enums.OrderStatus;

import java.time.LocalDateTime;

public record OrderResponse(
        String id,
        Long userId,
        Long flightId,
        Long hotelId,
        OrderStatus status,
        LocalDateTime createdAt
) {
}
