package org.booking.authservice.exception.custom;

import org.booking.authservice.exception.dto.ErrorCode;

public class ServiceUnavailableException extends BaseException {
    public ServiceUnavailableException(String message, Throwable cause) {
        super(message, ErrorCode.SERVICE_UNAVAILABLE, cause);
    }
}
