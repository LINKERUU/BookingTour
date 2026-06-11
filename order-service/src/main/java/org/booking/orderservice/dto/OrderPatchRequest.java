package org.booking.orderservice.dto;

import jakarta.validation.constraints.Positive;

public record OrderPatchRequest(
        @Positive(message = "User ID must be positive")
        String userId,

        @Positive(message = "Flight ID must be positive")
        String flightId,

        @Positive(message = "Hotel ID must be positive")
        String hotelId
) {
}
