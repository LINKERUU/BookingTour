package org.booking.hotelinventory.service;

import org.booking.sharedlib.messaging.event.BookingCommand;
import org.booking.sharedlib.messaging.result.ReservationResult;

import java.math.BigDecimal;

public interface HotelReservationService {

    ReservationResult reserve(String flightId, BigDecimal amount);

    void cancel(BookingCommand command);
}
