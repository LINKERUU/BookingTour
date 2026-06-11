package org.booking.orderservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderRequest(

        @NotNull(message = "User ID cannot be null")
        @Positive(message = "User ID must be positive")
        String userId,

        @NotNull(message = "Flight ID cannot be null")
        @Positive(message = "Flight ID must be positive")
        String flightId,

        @NotNull(message = "Hotel ID cannot be null")
        @Positive(message = "Hotel ID must be positive")
        String hotelId
) {
}
