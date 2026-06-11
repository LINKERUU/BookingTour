package org.booking.flightinventory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.booking.flightinventory.repository.FlightRepository;
import org.booking.flightinventory.service.FlightReservationService;
import org.booking.sharedlib.messaging.event.BookingCommand;
import org.booking.sharedlib.messaging.result.ReservationResult;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlightReservationServiceImpl implements FlightReservationService {

    private final FlightRepository flightRepository;

    @Override
    public ReservationResult reserve(String flightId, BigDecimal amount) {
        return flightRepository.findById(flightId)
                .map(flight -> {
                    if (flight.getAvailableSeats() <= 0) {
                        log.warn("No seats available flightId={}", flightId);
                        return ReservationResult.failure("No available seats for flight: " + flightId);
                    }
                    flight.reserveSeat();
                    flightRepository.save(flight);
                    log.info("Seat reserved flightId={}", flightId);
                    return ReservationResult.success(amount.add(flight.getPrice()));
                })
                .orElseGet(() -> {
                    log.warn("Flight not found flightId={}", flightId);
                    return ReservationResult.failure("Flight not found: " + flightId);
                });
    }

    @Override
    public void cancel(BookingCommand command) {
        flightRepository.findById(command.flightId()).ifPresent(flight -> {
            flight.releaseSeat();
            flightRepository.save(flight);
            log.info("Seat released orderId={}", command.orderId());
        });
    }
}
