package org.booking.authservice.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.booking.authservice.exception.custom.InvalidAmount;
import org.booking.authservice.exception.custom.UserNotFoundException;
import org.booking.authservice.service.UserService;
import org.booking.sharedlib.config.RabbitMQConstants;
import org.booking.sharedlib.messaging.event.BookingCommand;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserListener {

    private final UserService balanceService;
    private final UserReplyPublisher publisher;

    @RabbitListener(queues = RabbitMQConstants.USER_COMMAND_QUEUE)
    public void handleBalanceWithdraw(BookingCommand command) {

        try {
            balanceService.withdraw(command.userId(), command.amount());
            publisher.handleBalanceSuccess(command.orderId(), command.amount());
        }
        catch (InvalidAmount | UserNotFoundException ex) {
            publisher.handleBalanceFailure(command.orderId(), ex.getMessage());
        }

    }

    @RabbitListener(queues = RabbitMQConstants.USER_CANCEL_QUEUE)
    public void handleBalanceRefund(BookingCommand command) {
        log.info("Balance refund for orderId={}", command.orderId());
        balanceService.refund(command);
    }
}