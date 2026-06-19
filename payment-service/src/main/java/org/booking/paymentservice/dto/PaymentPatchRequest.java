package org.booking.paymentservice.dto;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PaymentPatchRequest(

        String orderId,

        @Positive(message = "Amount must be positive")
        BigDecimal amount
) {
}