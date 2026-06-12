package org.booking.authservice.service;

import org.booking.authservice.model.User;

public interface JwtService {

    String generateToken(User user);
    String extractEmail(String token);
    boolean isTokenValid(String token);
}
