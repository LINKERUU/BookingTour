package org.booking.orderservice.model.enums;

public enum OrderStatus {
    PENDING,
    FLIGHT_RESERVING,
    HOTEL_RESERVING,
    PAYMENT_PROCESSING,
    CONFIRMED,
    CANCELLED
}