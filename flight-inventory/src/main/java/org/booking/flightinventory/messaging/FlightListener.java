package org.booking.flightinventory.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.booking.flightinventory.exception.custom.FlightNotFoundException;
import org.booking.flightinventory.exception.custom.NoAvailableSeatsException;
import org.booking.flightinventory.service.FlightReservationService;
import org.booking.sharedlib.config.RabbitMQConstants;
import org.booking.sharedlib.messaging.event.BookingCommand;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class FlightListener {

    private final FlightReservationService reservationService;
    private final FlightReplyPublisher publisher;

    @RabbitListener(queues = RabbitMQConstants.FLIGHT_COMMAND_QUEUE)
    public void handleReserve(BookingCommand command) {

        try {
            BigDecimal amount = reservationService.reserve(command.flightId());
            publisher.handleFlightSuccess(command.orderId(), amount);
        }
        catch (NoAvailableSeatsException | FlightNotFoundException | OptimisticLockingFailureException ex) {
            publisher.handleFlightFailure(command.orderId(), ex.getMessage());
        }
    }

    @RabbitListener(queues = RabbitMQConstants.FLIGHT_CANCEL_QUEUE)
    public void handleCancel(BookingCommand command) {
        log.info("Flight cancel for orderId={}", command.orderId());
        reservationService.cancel(command);
    }
}