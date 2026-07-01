package org.booking.authservice.dto;

import java.math.BigDecimal;

public record UserResponse (
        String userId,
        String email,
        BigDecimal balance
){}

