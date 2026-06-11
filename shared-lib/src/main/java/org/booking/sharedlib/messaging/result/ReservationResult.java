package org.booking.sharedlib.messaging.result;

import java.math.BigDecimal;

public record ReservationResult(
        boolean success,
        BigDecimal amount,
        String reason
) {
    public static ReservationResult success(BigDecimal amount) {
        return new ReservationResult(true, amount, null);
    }

    public static ReservationResult failure(String reason) {
        return new ReservationResult(false, null, reason);
    }
}