package org.booking.hotelinventory.exception.custom;

import org.booking.hotelinventory.exception.dto.ErrorCode;

public class ServiceUnavailableException extends BaseException {
    public ServiceUnavailableException(String message, Throwable cause) {
        super(message, ErrorCode.SERVICE_UNAVAILABLE, cause);
    }
}
