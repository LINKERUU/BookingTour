package org.booking.authservice.dto;

public record AuthResponse(
    String token,
    String username,
    String userId,
    String email,
    String role
){}
