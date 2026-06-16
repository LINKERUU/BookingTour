package org.booking.flightinventory.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.booking.flightinventory.service.FlightReservationService;
import org.booking.sharedlib.config.RabbitMQConstants;
import org.booking.sharedlib.messaging.event.BookingCommand;
import org.booking.sharedlib.messaging.result.ReservationResult;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FlightListener {

    private final FlightReservationService reservationService;
    private final FlightReplyPublisher publisher;

    @RabbitListener(queues = RabbitMQConstants.FLIGHT_COMMAND_QUEUE)
    public void handleReserve(BookingCommand command) {

        ReservationResult result = reservationService.reserve(
                command.flightId(), command.amount()
        );

        if (result.success()) {
            log.info("Flight reserve for orderId={}", command.orderId());
            publisher.handleFlightSuccess(command, "Successfully reserved seat on flight", result.amount());
        } else {
            log.error("Failed to reserve seat on flight, orderId={}", command.orderId());
            publisher.handleFlightFailure(command, result.reason());
        }
    }

    @RabbitListener(queues = RabbitMQConstants.FLIGHT_CANCEL_QUEUE)
    public void handleCancel(BookingCommand command) {
        log.info("Flight cancel for orderId={}", command.orderId());
        reservationService.cancel(command);
    }
}