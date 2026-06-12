package org.booking.authservice.dto;

public record AuthResponse(
    String token,
    String userId,
    String email,
    String role
){}
