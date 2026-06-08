package org.booking.orderservice.exception.custom;

import org.booking.orderservice.exception.dto.ErrorCode;

public class ServiceUnavailableException extends BaseException{
    public ServiceUnavailableException(String message, Throwable cause){
        super(message, ErrorCode.SERVICE_UNAVAILABLE, cause);
    }
}
