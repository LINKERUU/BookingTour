package org.booking.hotelinventory.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.booking.hotelinventory.exception.custom.HotelNotFoundException;
import org.booking.hotelinventory.exception.custom.NoAvailableRoomsException;
import org.booking.hotelinventory.service.HotelReservationService;
import org.booking.sharedlib.config.RabbitMQConstants;
import org.booking.sharedlib.messaging.event.BookingCommand;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.dao.OptimisticLockingFailureException;
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

        try {
            BigDecimal amount = reservationService.reserve(command.hotelId());
            publisher.handleHotelSuccess(command.orderId(), amount);
        }
        catch (NoAvailableRoomsException | HotelNotFoundException | OptimisticLockingFailureException ex) {
            publisher.handleHotelFailure(command.orderId(), ex.getMessage());
        }
    }

    @RabbitListener(queues = RabbitMQConstants.HOTEL_CANCEL_QUEUE)
    public void handleCancel(BookingCommand command) {
        log.info("Hotel cancel for orderId={}", command.orderId());
        reservationService.cancel(command);
    }
}