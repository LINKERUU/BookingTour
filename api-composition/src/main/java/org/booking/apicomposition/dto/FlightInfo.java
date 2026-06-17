package org.booking.apicomposition.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FlightInfo(
        String id,
        String flightNumber,
        String departureFrom,
        String arrivalTo,
        LocalDateTime departureTime,
        BigDecimal price
) {
}
