package org.booking.authservice.exception.custom;

import org.booking.authservice.exception.dto.ErrorCode;

public class InvalidCredentialsException extends BaseException {
    public InvalidCredentialsException() {
        super("Некоректная почта или пароль", ErrorCode.INVALID_CREDENTIALS);
    }
}
