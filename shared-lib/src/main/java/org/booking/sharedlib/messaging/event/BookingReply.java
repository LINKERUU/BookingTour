package org.booking.sharedlib.messaging.event;

import java.math.BigDecimal;

public record BookingReply(String orderId, Boolean success, String reason, BigDecimal amount) {
    public static BookingReply success(BookingCommand command, String reason, BigDecimal amount) {
        return new BookingReply(command.orderId(), true, reason, amount);
    }

    public static BookingReply failure(BookingCommand command, String reason) {
        return new BookingReply(command.orderId(), false, reason, command.amount());
    }
}
