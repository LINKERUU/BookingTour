package org.booking.orderservice.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.booking.orderservice.model.Order;
import org.booking.orderservice.service.OrderStateService;
import org.booking.sharedlib.config.RabbitMQConstants;
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
        String cancelReason;

        switch (order.getStatus()) {
            case PAYMENT_PROCESSING -> {
                cancelReason = "Таймаут или критическая ошибка на этапе оплаты. Сага отменена.";
                log.warn("DLQ: {}, компенсируем Flight и Hotel", cancelReason);
                commandPublisher.cancelFlight(command);
                commandPublisher.cancelHotel(command);
            }
            case HOTEL_RESERVING -> {
                cancelReason = "Сервис отелей недоступен или вернул ошибку. Сага отменена.";
                log.warn("DLQ: {}, компенсируем Flight", cancelReason);
                commandPublisher.cancelFlight(command);
            }
            case FLIGHT_RESERVING -> {
                cancelReason = "Сервис авиарейсов недоступен. Заказ отменен.";
                log.warn("DLQ: {}", cancelReason);
            }
            default -> {
                cancelReason = "Неизвестная ошибка при обработке шагов Саги.";
                log.warn("DLQ: Unknown Order Status {} for order id: {}", order.getStatus(), command.orderId());
            }
        }

        orderStateService.cancel(order.getId(), cancelReason);
    }
}
