package org.booking.bookingui.service.impl;

import lombok.RequiredArgsConstructor;
import org.booking.bookingui.dto.AuthResponse;
import org.booking.bookingui.dto.LoginRequest;
import org.booking.bookingui.dto.RegisterRequest;
import org.booking.bookingui.service.AuthService;
import org.booking.bookingui.service.UtilsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UtilsService utilsService;

    @Override
    public AuthResponse login(LoginRequest request) {
        return utilsService.post("/api/auth/login", request, AuthResponse.class);
    }

    @Override
    public void register(RegisterRequest request) {
        utilsService.post("/api/auth/register", request, AuthResponse.class);
    }

    @Override
    public String checkToken(String token) {
        if (token == null) {
            return "redirect:/login";
        }
        return "redirect:/index";
    }
}
