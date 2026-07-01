package org.booking.paymentservice.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.booking.paymentservice.exception.custom.PaymentAlreadyProcessed;
import org.booking.paymentservice.exception.custom.PaymentNotFoundException;
import org.booking.paymentservice.service.PaymentProcessService;
import org.booking.sharedlib.config.RabbitMQConstants;
import org.booking.sharedlib.messaging.event.BookingCommand;
import org.booking.sharedlib.messaging.event.BookingReply;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentListener {

    private final PaymentReplyPublisher publisher;
    private final PaymentProcessService processService;

    @RabbitListener(queues = RabbitMQConstants.PAYMENT_COMMAND_QUEUE)
    public void handlePayment(BookingCommand command) {

        try {
            processService.createPayment(command);
            publisher.publishBalance(command, RabbitMQConstants.USER_COMMAND_KEY);
        }
        catch (PaymentAlreadyProcessed ex) {
            publisher.handlePaymentFailure(command.orderId(), ex.getMessage());
        }
    }

    @RabbitListener(queues = RabbitMQConstants.USER_REPLY_QUEUE)
    public void handleUserBalanceReply(BookingReply reply) {

        try {
            if (reply.success()) {
                log.info("User balance deducted. Completing payment for orderId={}", reply.orderId());

                processService.complete(reply.orderId());
                publisher.handlePaymentSuccess(reply.orderId(), reply.amount());
            } else {
                log.warn("User balance deduction failed for orderId={}: {}", reply.orderId(), reply.reason());

                processService.fail(reply.orderId());
                publisher.handlePaymentFailure(reply.orderId(), reply.reason());
            }
        }
        catch (PaymentNotFoundException ex) {
            publisher.handlePaymentFailure(reply.orderId(), ex.getMessage());
        }
    }

    @RabbitListener(queues = RabbitMQConstants.PAYMENT_CANCEL_QUEUE)
    public void handleCancel(BookingCommand command) {

        processService.refund(command.orderId());
        publisher.publishBalance(command, RabbitMQConstants.USER_CANCEL_KEY);

        log.info("Payment cancelled ={}", command);

    }
}