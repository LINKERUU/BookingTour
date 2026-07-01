package org.booking.orderservice.model.enums;

public enum OrderStatus {
    PENDING,
    FLIGHT_RESERVING,
    HOTEL_RESERVING,
    PAYMENT_PROCESSING,
    CONFIRMED,
    CANCELLED;

    public boolean canTransition(OrderStatus next) {
        return switch (this) {
            case PENDING -> next == OrderStatus.FLIGHT_RESERVING;
            case FLIGHT_RESERVING -> next == OrderStatus.HOTEL_RESERVING || next == OrderStatus.CANCELLED;
            case HOTEL_RESERVING -> next == OrderStatus.PAYMENT_PROCESSING || next == OrderStatus.CANCELLED;
            case PAYMENT_PROCESSING -> next == OrderStatus.CONFIRMED || next == OrderStatus.CANCELLED;
            default -> false;
        };
    }
}

