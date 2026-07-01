package org.booking.flightinventory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.booking.flightinventory.exception.custom.FlightNotFoundException;
import org.booking.flightinventory.exception.custom.NoAvailableSeatsException;
import org.booking.flightinventory.model.Flight;
import org.booking.flightinventory.repository.FlightRepository;
import org.booking.flightinventory.service.FlightReservationService;
import org.booking.sharedlib.messaging.event.BookingCommand;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Slf4j
@Service
@RequiredArgsConstructor
public class FlightReservationServiceImpl implements FlightReservationService {

    private final FlightRepository flightRepository;

    @Override
    public BigDecimal reserve(String flightId) {

        Flight flight = getFlight(flightId);

        if (flight.getAvailableSeats() <= 0) {
            throw new NoAvailableSeatsException();
        }

        flight.reserveSeat();
        flightRepository.save(flight);

        return flight.getPrice();
    }

    @Override
    public void cancel(BookingCommand command) {
        Flight flight= getFlight(command.flightId());

        flight.releaseSeat();
        flightRepository.save(flight);

        log.info("Seat released orderId={}", command.orderId());
    }

    private Flight getFlight(String flightId) {
        return flightRepository.findById(flightId)
                .orElseThrow(FlightNotFoundException::new);
    }
}