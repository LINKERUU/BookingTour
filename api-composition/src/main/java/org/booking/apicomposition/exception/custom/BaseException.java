package org.booking.apicomposition.exception.custom;

import lombok.Getter;
import org.booking.apicomposition.exception.dto.ErrorCode;

@Getter
public class BaseException extends RuntimeException {

    private final ErrorCode errorCode;

    protected BaseException(String message, ErrorCode errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

}
