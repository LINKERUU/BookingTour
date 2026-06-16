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

    public void handlePaymentSuccess(BookingCommand command, String reason, BigDecimal amount) {
        publish(BookingReply.success(command, reason, amount));
    }

    public void handlePaymentFailure(BookingCommand command, String reason) {
        publish(BookingReply.failure(command, reason));
    }

    private void publish(BookingReply reply) {
        rabbitTemplate.convertAndSend(RabbitMQConstants.BOOKING_EXCHANGE, RabbitMQConstants.PAYMENT_REPLY_KEY, reply);
    }
}
