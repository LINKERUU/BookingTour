package org.booking.apicomposition.exception.custom;


import org.booking.apicomposition.exception.dto.ErrorCode;

public class ServiceUnavailableException extends BaseException {
    public ServiceUnavailableException(String message){
        super(message, ErrorCode.SERVICE_UNAVAILABLE);
    }
}
