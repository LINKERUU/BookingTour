package org.booking.hotelinventory.service;

import org.booking.sharedlib.messaging.event.BookingCommand;

import java.math.BigDecimal;

public interface HotelReservationService {

    BigDecimal reserve(String flightId);

    void cancel(BookingCommand command);
}
