package org.booking.paymentservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.booking.paymentservice.exception.custom.PaymentNotFoundException;
import org.booking.paymentservice.model.Payment;
import org.booking.paymentservice.model.enums.PaymentStatus;
import org.booking.paymentservice.repository.PaymentRepository;
import org.booking.paymentservice.service.PaymentProcessService;
import org.booking.sharedlib.messaging.event.BookingCommand;
import org.booking.sharedlib.messaging.result.ReservationResult;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentProcessServiceImpl implements PaymentProcessService {

    private final PaymentRepository paymentRepository;

    @Override
    public ReservationResult processPayment(BookingCommand command) {


        if (command.amount() == null || command.amount().compareTo(BigDecimal.ZERO) <= 0) {
            log.warn("Invalid amount for orderId={}", command.orderId());
            return ReservationResult.failure("Invalid payment amount");
        }

        if (paymentRepository.existsByOrderId(command.orderId())) {
            log.warn("Payment already processed for orderId={}", command.orderId());
            return ReservationResult.failure("Payment already processed");
        }

        Payment payment = new Payment(
                command.orderId(),
                command.userId(),
                command.amount()
        );

        payment.changeStatus(PaymentStatus.COMPLETED);
        paymentRepository.save(payment);

        log.info("Payment completed for orderId={} amount={}",
                command.orderId(), command.amount());

        return ReservationResult.success(command.amount());
    }


    @Override
    public void refund(BookingCommand command) {
        Payment payment = paymentRepository.findByOrderId(command.orderId())
                .orElseThrow(() -> new PaymentNotFoundException(command.orderId()));

        payment.changeStatus(PaymentStatus.REFUNDED);
        paymentRepository.save(payment);
    }

}