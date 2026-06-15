package org.booking.sharedlib.security.dto;

public record JwtUserInfo(
        String userId,
        String email,
        String role
) {
}
