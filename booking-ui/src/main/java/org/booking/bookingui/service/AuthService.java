package org.booking.bookingui.service;

import org.booking.bookingui.dto.AuthResponse;
import org.booking.bookingui.dto.LoginRequest;
import org.booking.bookingui.dto.RegisterRequest;

public interface AuthService {

    AuthResponse login(LoginRequest request);
    void register(RegisterRequest request);
    String checkToken(String token);
}
