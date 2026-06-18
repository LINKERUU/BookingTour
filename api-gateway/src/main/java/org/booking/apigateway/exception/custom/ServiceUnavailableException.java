package org.booking.apigateway.exception.custom;


import org.booking.apigateway.exception.dto.ErrorCode;

public class ServiceUnavailableException extends BaseException {
    public ServiceUnavailableException(String message, Throwable cause) {
        super(message, ErrorCode.SERVICE_UNAVAILABLE, cause);
    }
}
