package org.booking.flightinventory.service;

import org.booking.sharedlib.messaging.event.BookingCommand;

import java.math.BigDecimal;

public interface FlightReservationService {

    BigDecimal reserve(String flightId);

    void cancel(BookingCommand command);
}
