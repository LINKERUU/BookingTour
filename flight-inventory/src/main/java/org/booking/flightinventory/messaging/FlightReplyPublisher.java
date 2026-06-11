package org.booking.flightinventory.messaging;

import lombok.RequiredArgsConstructor;
import org.booking.sharedlib.messaging.config.RabbitMQConstants;
import org.booking.sharedlib.messaging.event.BookingCommand;
import org.booking.sharedlib.messaging.event.BookingReply;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class FlightReplyPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void handleFlightSuccess(BookingCommand command, String reason, BigDecimal amount) {
        publish(BookingReply.success(command, reason, amount));
    }

    public void handleFlightFailure(BookingCommand command, String reason) {
        publish(BookingReply.failure(command, reason));
    }

    private void publish(BookingReply reply) {
        rabbitTemplate.convertAndSend(RabbitMQConstants.BOOKING_EXCHANGE, RabbitMQConstants.FLIGHT_REPLY_KEY, reply);
    }
}
