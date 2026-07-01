package org.booking.paymentservice.messaging;

import lombok.RequiredArgsConstructor;
import org.booking.sharedlib.config.RabbitMQConstants;
import org.booking.sharedlib.messaging.event.BookingCommand;
import org.booking.sharedlib.messaging.event.BookingReply;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class PaymentReplyPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void handlePaymentSuccess(String orderId, BigDecimal amount) {
        publish(BookingReply.success(orderId, amount));
    }

    public void handlePaymentFailure(String orderId, String reason) {
        publish(BookingReply.failure(orderId, reason));
    }

    private void publish(BookingReply reply) {
        rabbitTemplate.convertAndSend(RabbitMQConstants.BOOKING_EXCHANGE, RabbitMQConstants.PAYMENT_REPLY_KEY, reply);
    }

    public void publishBalance(BookingCommand command, String routingKey) {
        rabbitTemplate.convertAndSend(RabbitMQConstants.BOOKING_EXCHANGE, routingKey, command);
    }
}
