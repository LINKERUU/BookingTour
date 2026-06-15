package org.booking.orderservice.messaging;

import lombok.RequiredArgsConstructor;
import org.booking.sharedlib.config.RabbitMQConstants;
import org.booking.sharedlib.messaging.event.BookingReply;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingReplyListener {

    private final SagaOrchestrator orchestrator;

    @RabbitListener(queues = RabbitMQConstants.FLIGHT_REPLY_QUEUE)
    public void handleFlightReply(BookingReply reply) {
        if (reply.success())
            orchestrator.handleFlightSuccess(reply);
        else
            orchestrator.handleFlightFailure(reply);
    }

    @RabbitListener(queues = RabbitMQConstants.HOTEL_REPLY_QUEUE)
    public void handleHotelReply(BookingReply reply) {
        if (reply.success())
            orchestrator.handleHotelSuccess(reply);
        else
            orchestrator.handleHotelFailure(reply);
    }

    @RabbitListener(queues = RabbitMQConstants.PAYMENT_REPLY_QUEUE)
    public void handlePaymentReply(BookingReply reply) {
        if (reply.success())
            orchestrator.handlePaymentSuccess(reply);
        else
            orchestrator.handlePaymentFailure(reply);
    }


}
