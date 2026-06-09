package org.booking.flightinventory.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FlightResponse(
        String id,
        String flightNumber,
        String departureFrom,
        String arrivalTo,
        LocalDateTime departureTime,
        Integer availableSeats,
        BigDecimal price
) {
}
