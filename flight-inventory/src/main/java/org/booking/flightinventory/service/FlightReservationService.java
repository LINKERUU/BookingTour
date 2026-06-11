package org.booking.flightinventory.service;

import org.booking.sharedlib.messaging.event.BookingCommand;
import org.booking.sharedlib.messaging.result.ReservationResult;

import java.math.BigDecimal;

public interface FlightReservationService {

    ReservationResult reserve(String flightId, BigDecimal amount);

    void cancel(BookingCommand command);
}
