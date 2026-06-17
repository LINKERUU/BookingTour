package org.booking.apicomposition.dto;

import java.math.BigDecimal;

public record PaymentInfo(
        String id,
        BigDecimal amount,
        String status
) {
}
