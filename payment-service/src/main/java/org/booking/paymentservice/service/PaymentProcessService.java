package org.booking.paymentservice.service;

import org.booking.sharedlib.messaging.event.BookingCommand;

public interface PaymentProcessService {

    void createPayment(BookingCommand bookingCommand);

    void refund(String orderId);

    void complete(String orderId);

    void fail(String orderId);
}
