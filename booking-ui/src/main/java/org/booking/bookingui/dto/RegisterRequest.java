package org.booking.bookingui.dto;


public record RegisterRequest(
        String username,
        String email,
        String password
) {
}

