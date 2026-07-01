package org.booking.authservice.exception.custom;

import org.booking.authservice.exception.dto.ErrorCode;

public class UserAlreadyExistsException extends BaseException {
    public UserAlreadyExistsException(String email) {
        super("Пользователь с этой почтой уже существует:  " + email, ErrorCode.EMAIL_ALREADY_EXISTS);
    }
}
