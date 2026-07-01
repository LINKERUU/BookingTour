package org.booking.orderservice.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.booking.orderservice.mapper.OrderMapper;
import org.booking.orderservice.model.Order;
import org.booking.orderservice.model.enums.OrderStatus;
import org.booking.orderservice.service.OrderStateService;
import org.booking.sharedlib.messaging.event.BookingCommand;
import org.booking.sharedlib.messaging.event.BookingReply;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class SagaOrchestrator {

    private final OrderStateService orderStateService;
    private final BookingCommandPublisher commandPublisher;
    private final OrderMapper orderMapper;

    public void startSaga(BookingCommand command) {
        log.info("SagaOrchestrator start for id: {}", command.orderId());

        orderStateService.updateOrderStatus(command.orderId(), OrderStatus.FLIGHT_RESERVING);

        commandPublisher.reserveFlight(command);
    }

    public void handleFlightSuccess(BookingReply reply) {
        log.info("[FLIGHT_RESERVING]SagaOrchestrator handleFlightReply for id: {}", reply.orderId());

        proceed(reply, OrderStatus.HOTEL_RESERVING, commandPublisher::reserveHotel);
    }

    public void handleFlightFailure(BookingReply reply) {
        log.info("Canceled Flight reply for id: {}", reply.orderId());
        orderStateService.cancel(reply.orderId(), reply.reason());
    }

    public void handleHotelSuccess(BookingReply reply) {
        log.info("[HOTEL_RESERVING]SagaOrchestrator handleHotelReply for id: {}", reply.orderId());

        proceed(reply, OrderStatus.PAYMENT_PROCESSING, commandPublisher::processPayment);
    }

    public void handleHotelFailure(BookingReply reply) {
        log.info("Canceled Hotel reply for id: {}", reply.orderId());

        commandPublisher.cancelFlight(cancel(reply));

    }

    public void handlePaymentSuccess(BookingReply reply) {
        log.info("[PAYMENT_PROCESSING]SagaOrchestrator handlePaymentReply for id: {}", reply.orderId());

        orderStateService.updateOrderStatus(reply.orderId(), OrderStatus.CONFIRMED);

        log.info("Saga COMPLETED for orderId: {}", reply.orderId());
    }

    public void handlePaymentFailure(BookingReply reply) {
        log.info("Canceled Payment reply for id: {}", reply.orderId());

        BookingCommand command = cancel(reply);

        commandPublisher.cancelFlight(command);
        commandPublisher.cancelHotel(command);
    }

    private void proceed(BookingReply reply, OrderStatus nextStatus, Consumer<BookingCommand> publisher) {
        Order order = orderStateService.updateOrder(reply.orderId(), reply.amount(), nextStatus, nextStatus);
        publisher.accept(orderMapper.toBookingCommand(order));
    }

    private BookingCommand cancel(BookingReply reply) {

        Order order = orderStateService.cancel(reply.orderId(), reply.reason());

        return orderMapper.toBookingCommand(order);
    }

}
