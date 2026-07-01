package org.booking.paymentservice.exception.dto;

public enum ErrorCode {
    PAYMENT_NOT_FOUND,
    PAYMENT_ALREADY_PROCESSED,
    SERVICE_UNAVAILABLE,
    FORBIDDEN,
    VALIDATION_FAILED,
    INTERNAL_SERVER_ERROR
}
