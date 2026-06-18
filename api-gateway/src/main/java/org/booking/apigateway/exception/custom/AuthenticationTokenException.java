package org.booking.apigateway.exception.custom;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationTokenException extends AuthenticationException {
    public AuthenticationTokenException(String message) {
        super(message);
    }
}
