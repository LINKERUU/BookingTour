package org.booking.paymentservice.exception.custom;

import org.booking.paymentservice.exception.dto.ErrorCode;

public class PaymentNotFoundException extends BaseException {

    public PaymentNotFoundException(String id) {
        super("Order not found with id: " + id, ErrorCode.PAYMENT_NOT_FOUND);
    }
}
