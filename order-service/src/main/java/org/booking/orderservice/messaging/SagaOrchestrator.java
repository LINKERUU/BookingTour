package org.booking.orderservice.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.booking.orderservice.mapper.OrderMapper;
import org.booking.orderservice.model.enums.OrderStatus;
import org.booking.orderservice.service.OrderStateService;
import org.booking.sharedlib.messaging.event.BookingCommand;
import org.booking.sharedlib.messaging.event.BookingReply;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class SagaOrchestrator {

    private final OrderStateService orderStateService;
    private final BookingCommandPublisher commandPublisher;
    private final OrderMapper orderMapper;

    public void startSaga(BookingCommand command) {
        log.info("SagaOrchestrator start for id: {}", command.orderId());

        orderStateService.changeStatus(command.orderId(), BigDecimal.ZERO,OrderStatus.FLIGHT_RESERVING);

        commandPublisher.reserveFlight(command);
    }

    public void handleFlightSuccess(BookingReply reply) {
        log.info("[FLIGHT_RESERVING]SagaOrchestrator handleFlightReply for id: {}", reply.orderId());

        orderStateService.changeStatus(reply.orderId(), reply.amount(),OrderStatus.HOTEL_RESERVING);

        BookingCommand command = orderMapper.toBookingCommand(orderStateService.getOrder(reply.orderId()));

        commandPublisher.reserveHotel(command);

    }

    public void handleFlightFailure(BookingReply reply) {
        log.info("Canceled Flight reply for id: {}", reply.orderId());
        orderStateService.cancel(reply.orderId());
    }

    public void handleHotelSuccess(BookingReply reply) {
        log.info("[HOTEL_RESERVING]SagaOrchestrator handleHotelReply for id: {}", reply.orderId());

        orderStateService.changeStatus(reply.orderId(), reply.amount(),OrderStatus.PAYMENT_PROCESSING);

        BookingCommand command = orderMapper.toBookingCommand(orderStateService.getOrder(reply.orderId()));

        commandPublisher.processPayment(command);

    }

    public void handleHotelFailure(BookingReply reply) {
        log.info("Canceled Hotel reply for id: {}", reply.orderId());
        orderStateService.cancel(reply.orderId());

        BookingCommand compensation = orderMapper.toBookingCommand(orderStateService.getOrder(reply.orderId()));

        commandPublisher.cancelFlight(compensation);

    }

    public void handlePaymentSuccess(BookingReply reply) {
        log.info("[PAYMENT_PROCESSING]SagaOrchestrator handlePaymentReply for id: {}", reply.orderId());

        orderStateService.confirm(reply.orderId(), reply.amount());

        log.info("Saga COMPLETED for orderId: {}", reply.orderId());
    }

    public void handlePaymentFailure(BookingReply reply) {
        log.info("Canceled Payment reply for id: {}", reply.orderId());
        orderStateService.cancel(reply.orderId());

        BookingCommand compensation = orderMapper.toBookingCommand(orderStateService.getOrder(reply.orderId()));

        commandPublisher.cancelFlight(compensation);
        commandPublisher.cancelHotel(compensation);

    }

}
