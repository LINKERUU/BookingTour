package org.booking.authservice.service;

import org.booking.authservice.dto.AuthResponse;
import org.booking.authservice.dto.LoginRequest;
import org.booking.authservice.dto.RegisterRequest;

public interface AuthService {

    AuthResponse login(LoginRequest user);

    AuthResponse register(RegisterRequest user);
}
