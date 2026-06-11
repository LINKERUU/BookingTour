package org.booking.orderservice.messaging;

import lombok.RequiredArgsConstructor;
import org.booking.sharedlib.messaging.config.RabbitMQConstants;
import org.booking.sharedlib.messaging.event.BookingCommand;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingCommandPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void reserveFlight(BookingCommand command) {
        send(RabbitMQConstants.FLIGHT_COMMAND_KEY, command);
    }

    public void reserveHotel(BookingCommand command) {
        send(RabbitMQConstants.HOTEL_COMMAND_KEY, command);
    }

    public void processPayment(BookingCommand command) {
        send(RabbitMQConstants.PAYMENT_COMMAND_KEY, command);
    }

    public void cancelFlight(BookingCommand command) {
        send(RabbitMQConstants.FLIGHT_CANCEL_KEY, command);
    }

    public void cancelHotel(BookingCommand command) {
        send(RabbitMQConstants.HOTEL_CANCEL_KEY, command);
    }

    private void send(String routingKey, BookingCommand command) {
        rabbitTemplate.convertAndSend(RabbitMQConstants.BOOKING_EXCHANGE, routingKey, command);
    }
}
