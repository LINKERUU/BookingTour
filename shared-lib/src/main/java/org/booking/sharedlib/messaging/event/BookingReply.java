package org.booking.sharedlib.messaging.event;

import java.math.BigDecimal;

public record BookingReply(
        String orderId,
        Boolean success,
        String reason,
        BigDecimal amount)
{
    public static BookingReply success(String orderId, BigDecimal amount) {
        return new BookingReply(orderId, true, null, amount);
    }

    public static BookingReply failure(String orderId, String reason) {
        return new BookingReply(orderId, false,reason, BigDecimal.ZERO);
    }
}