package org.booking.orderservice.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.booking.orderservice.model.Order;
import org.booking.orderservice.service.OrderStateService;
import org.booking.sharedlib.messaging.config.RabbitMQConstants;
import org.booking.sharedlib.messaging.event.BookingCommand;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeadLetterHandler {

    private final OrderStateService orderStateService;
    private final BookingCommandPublisher commandPublisher;

    @RabbitListener(queues = RabbitMQConstants.DEAD_LETTER_QUEUE)
    public void handleDeadLetter(BookingCommand command) {
        Order order = orderStateService.getOrder(command.orderId());

        switch (order.getStatus()) {

            case PAYMENT_PROCESSING -> {
                commandPublisher.cancelFlight(command);
                commandPublisher.cancelHotel(command);
            }
            case HOTEL_RESERVING -> commandPublisher.cancelFlight(command);

            case FLIGHT_RESERVING ->
                    log.warn("Order has been cancelled on Flight reservation step for order id: {}", command.orderId());

            default -> log.warn("Unknown Order Status for order id: {}", command.orderId());
        }

        orderStateService.cancel(order.getId());
    }
}
