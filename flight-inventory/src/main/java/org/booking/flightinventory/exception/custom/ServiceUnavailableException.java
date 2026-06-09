package org.booking.flightinventory.exception.custom;

import org.booking.flightinventory.exception.dto.ErrorCode;

public class ServiceUnavailableException extends BaseException {
    public ServiceUnavailableException(String message, Throwable cause){
        super(message, ErrorCode.SERVICE_UNAVAILABLE, cause);
    }
}
