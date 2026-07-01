package org.booking.authservice.exception.custom;

import org.booking.authservice.exception.dto.ErrorCode;

public class InvalidAmount extends BaseException {
    public InvalidAmount(String message) {
        super(message, ErrorCode.INVALID_AMOUNT_WITHDRAW);
    }
}
