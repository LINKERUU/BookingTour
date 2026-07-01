package org.booking.paymentservice.exception.custom;

import org.booking.paymentservice.exception.dto.ErrorCode;

public class PaymentAlreadyProcessed extends BaseException {
    public PaymentAlreadyProcessed(String id) {
        super("Платеж уже обработан" + id, ErrorCode.PAYMENT_ALREADY_PROCESSED);
    }
}
