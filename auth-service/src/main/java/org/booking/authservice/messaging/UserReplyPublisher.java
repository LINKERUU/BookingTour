package org.booking.authservice.messaging;

import lombok.RequiredArgsConstructor;
import org.booking.sharedlib.config.RabbitMQConstants;
import org.booking.sharedlib.messaging.event.BookingReply;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class UserReplyPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void handleBalanceSuccess(String orderId, BigDecimal amount) {
        publish(BookingReply.success(orderId, amount));
    }

    public void handleBalanceFailure(String orderId, String reason) {
        publish(BookingReply.failure(orderId, reason));
    }

    private void publish(BookingReply reply) {
        rabbitTemplate.convertAndSend(RabbitMQConstants.BOOKING_EXCHANGE, RabbitMQConstants.USER_REPLY_KEY, reply);
    }
}
