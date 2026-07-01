package org.booking.paymentservice.exception.custom;

import org.booking.paymentservice.exception.dto.ErrorCode;

public class PaymentNotFoundException extends BaseException {

    public PaymentNotFoundException(String id) {
        super("Платеж не найден" + id, ErrorCode.PAYMENT_NOT_FOUND);
    }
}
