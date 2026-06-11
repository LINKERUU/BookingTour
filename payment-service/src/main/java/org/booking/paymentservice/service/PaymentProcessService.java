package org.booking.paymentservice.service;

import org.booking.sharedlib.messaging.event.BookingCommand;
import org.booking.sharedlib.messaging.result.ReservationResult;

public interface PaymentProcessService {

    ReservationResult processPayment(BookingCommand command);

    void refund(BookingCommand command);
}
