package org.booking.authservice.exception.custom;

import org.booking.authservice.exception.dto.ErrorCode;

public class UserAlreadyExistsException extends BaseException {
    public UserAlreadyExistsException(String email) {
        super("User with this email already exists:  " + email, ErrorCode.EMAIL_ALREADY_EXISTS);
    }
}
