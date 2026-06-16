package org.booking.paymentservice.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.booking.paymentservice.service.PaymentProcessService;
import org.booking.sharedlib.config.RabbitMQConstants;
import org.booking.sharedlib.messaging.event.BookingCommand;
import org.booking.sharedlib.messaging.result.ReservationResult;
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

        ReservationResult result = processService.processPayment(command);

        if (result.success()) {

            log.info("Payment successful for orderId={}", command.orderId());
            publisher.handlePaymentSuccess(command, "Payment completed", result.amount());
        } else
            publisher.handlePaymentFailure(command, result.reason());

    }

    @RabbitListener(queues = RabbitMQConstants.PAYMENT_CANCEL_QUEUE)
    public void handleCancel(BookingCommand command) {
        log.info("Payment cancel for orderId={}", command.orderId());

        processService.refund(command);

    }

}