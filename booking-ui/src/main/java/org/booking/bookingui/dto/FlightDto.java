package org.booking.bookingui.dto;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FlightDto(
        String id,
        String flightNumber,
        String departureFrom,
        String arrivalTo,
        LocalDateTime departureTime,
        Integer availableSeats,
        BigDecimal price
) {}