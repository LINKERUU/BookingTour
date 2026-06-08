package org.booking.orderservice.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    PENDING,
    PAYMENT_PROCESSING,
    FLIGHT_RESERVING,
    HOTEL_RESERVING,
    CONFIRMED,
    CANCELLED
}