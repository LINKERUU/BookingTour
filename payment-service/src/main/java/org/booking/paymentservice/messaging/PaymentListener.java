package org.booking.paymentservice.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.booking.paymentservice.model.Payment;
import org.booking.paymentservice.model.enums.PaymentStatus;
import org.booking.paymentservice.repository.PaymentRepository;
import org.booking.sharedlib.config.RabbitMQConstants;
import org.booking.sharedlib.messaging.event.BookingCommand;
import org.booking.sharedlib.messaging.event.BookingReply;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentListener {

    private final RabbitTemplate rabbitTemplate;
    private final PaymentRepository paymentRepository;

    @RabbitListener(queues = RabbitMQConstants.PAYMENT_COMMAND_QUEUE)
    public void handlePayment(BookingCommand command) {
        log.info("Payment command for orderId={} amount={}",
                command.orderId(), command.amount());
        try {
            Payment payment = new Payment(
                    command.orderId(),
                    command.userId(),
                    command.amount()
            );
            payment.changeStatus(PaymentStatus.COMPLETED);
            paymentRepository.save(payment);

            log.info("Payment completed for orderId={}", command.orderId());
            sendReply(command.orderId(), true, "Payment completed",command.amount());

        } catch (Exception e) {
            log.error("Payment failed for orderId={}", command.orderId(), e);
            sendReply(command.orderId(), false, "Payment failed: " + e.getMessage(),command.amount());
        }
    }

    @RabbitListener(queues = RabbitMQConstants.PAYMENT_CANCEL_QUEUE)
    public void handleCancel(BookingCommand command) {
        log.info("Payment cancel for orderId={}", command.orderId());

        paymentRepository.findByOrderId(command.orderId())
                .ifPresent(payment -> {
                    payment.changeStatus(PaymentStatus.REFUNDED);
                    paymentRepository.save(payment);
                    log.info("Payment refunded for orderId={}", command.orderId());
                });
    }

    private void sendReply(String orderId, boolean success, String reason, BigDecimal amount) {
        BookingReply reply = new BookingReply(orderId, success, reason, amount);
        rabbitTemplate.convertAndSend(RabbitMQConstants.BOOKING_EXCHANGE, RabbitMQConstants.PAYMENT_REPLY_KEY, reply);
    }
}