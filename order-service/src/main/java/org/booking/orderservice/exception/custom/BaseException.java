package org.booking.orderservice.exception.custom;

import lombok.Getter;
import org.booking.orderservice.exception.dto.ErrorCode;

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
