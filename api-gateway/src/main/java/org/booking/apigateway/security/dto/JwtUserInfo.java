package org.booking.apigateway.security.dto;

public record JwtUserInfo(
        String userId,
        String email,
        String role
) {
}
