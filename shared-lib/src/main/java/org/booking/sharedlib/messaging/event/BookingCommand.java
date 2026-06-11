package org.booking.sharedlib.messaging.event;

import java.math.BigDecimal;

public record BookingCommand(
        String orderId,
        String userId,
        String flightId,
        String hotelId,
        BigDecimal amount
) {
}
