package org.booking.authservice.exception.custom;

import org.booking.authservice.exception.dto.ErrorCode;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException(String id) {
        super("User not found with id: " + id, ErrorCode.USER_NOT_FOUND);
    }
}
