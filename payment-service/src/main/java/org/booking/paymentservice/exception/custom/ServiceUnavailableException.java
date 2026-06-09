package org.booking.paymentservice.exception.custom;

import org.booking.paymentservice.exception.dto.ErrorCode;

public class ServiceUnavailableException extends BaseException {
    public ServiceUnavailableException(String message, Throwable cause) {
        super(message, ErrorCode.SERVICE_UNAVAILABLE, cause);
    }
}
