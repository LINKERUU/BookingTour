package org.booking.authservice.exception.custom;

import org.booking.authservice.exception.dto.ErrorCode;

public class InvalidCredentialsException extends BaseException {
    public InvalidCredentialsException() {
        super("Invalid email or password", ErrorCode.INVALID_CREDENTIALS);
    }
}
