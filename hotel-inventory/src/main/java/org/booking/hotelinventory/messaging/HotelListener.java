package org.booking.hotelinventory.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.booking.hotelinventory.service.HotelReservationService;
import org.booking.sharedlib.config.RabbitMQConstants;
import org.booking.sharedlib.messaging.event.BookingCommand;
import org.booking.sharedlib.messaging.result.ReservationResult;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class HotelListener {

    private final HotelReservationService reservationService;
    private final HotelReplyPublisher publisher;

    @RabbitListener(queues = RabbitMQConstants.HOTEL_COMMAND_QUEUE)
    public void handleReserve(BookingCommand command) {


        ReservationResult result = reservationService.reserve(
                command.hotelId(), command.amount()
        );

        if (result.success()) {
            BigDecimal totalAmount = command.amount().add(result.amount());
            publisher.handleHotelSuccess(command, "Successfully reserved room in Hotel", totalAmount);
        } else
            publisher.handleHotelFailure(command, result.reason());

        log.info("Hotel reserve for orderId={}", command.orderId());

    }

    @RabbitListener(queues = RabbitMQConstants.HOTEL_CANCEL_QUEUE)
    public void handleCancel(BookingCommand command) {
        log.info("Hotel cancel for orderId={}", command.orderId());
        reservationService.cancel(command);
    }
}