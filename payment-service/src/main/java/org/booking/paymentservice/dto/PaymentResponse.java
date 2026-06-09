package org.booking.paymentservice.dto;

import org.booking.paymentservice.model.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponse(
        String id,
        String orderId,
        String userId,
        BigDecimal amount,
        PaymentStatus status,
        LocalDateTime createdAt
) {
}
