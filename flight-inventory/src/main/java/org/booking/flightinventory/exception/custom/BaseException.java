package org.booking.flightinventory.exception.custom;

import lombok.Getter;
import org.booking.flightinventory.exception.dto.ErrorCode;

@Getter
public class BaseException extends RuntimeException {

    private final ErrorCode errorCode;

    protected BaseException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    protected BaseException(String message, ErrorCode errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

}
