package org.booking.orderservice.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    PENDING,
    FLIGHT_RESERVING,
    HOTEL_RESERVING,
    PAYMENT_PROCESSING,
    CONFIRMED,
    CANCELLED
}