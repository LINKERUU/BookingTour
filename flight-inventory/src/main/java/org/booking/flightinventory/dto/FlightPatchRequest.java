package org.booking.flightinventory.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FlightPatchRequest(
        @Size(min = 2, max = 10, message = "Flight number must be between 2 and 10 characters")
        String flightNumber ,

        String departureFrom,

        String arrivalTo,

        @Future(message = "Departure time must be in the future")
        LocalDateTime departureTime,

        @PositiveOrZero(message = "Available seats must be positive or zero")
        Integer availableSeats,

        @Positive(message = "Price must be positive")
        BigDecimal price
) {
}
