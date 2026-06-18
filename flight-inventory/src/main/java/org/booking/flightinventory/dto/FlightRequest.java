package org.booking.flightinventory.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FlightRequest(

        @NotBlank(message = "Flight number cannot be blank")
        @Size(min = 2, max = 10, message = "Flight number must be between 2 and 10 characters")
        String flightNumber ,

        @NotBlank(message = "Departure place cannot be blank")
        String departureFrom,

        @NotBlank(message = "Arrival place cannot be blank")
        String arrivalTo,

        @NotNull(message = "Departure time is required")
        @Future(message = "Departure time must be in the future")
        LocalDateTime departureTime,

        @NotNull(message = "Available seats count is required")
        @PositiveOrZero(message = "Available seats must be positive or zero")
        Integer availableSeats,

        @NotNull(message = "Price cannot be null")
        @Positive(message = "Price must be positive")
        BigDecimal price
) {
}
