package org.booking.paymentservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.booking.paymentservice.exception.custom.PaymentAlreadyProcessed;
import org.booking.paymentservice.exception.custom.PaymentNotFoundException;
import org.booking.paymentservice.mapper.PaymentMapper;
import org.booking.paymentservice.model.Payment;
import org.booking.paymentservice.model.enums.PaymentStatus;
import org.booking.paymentservice.repository.PaymentRepository;
import org.booking.paymentservice.service.PaymentProcessService;
import org.booking.sharedlib.messaging.event.BookingCommand;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentProcessServiceImpl implements PaymentProcessService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public void createPayment(BookingCommand command) {

        if (paymentRepository.existsByOrderId(command.orderId())) {
            throw new PaymentAlreadyProcessed(command.orderId());
        }

        Payment payment = paymentMapper.toPayment(command);
        paymentRepository.save(payment);

        log.info("Payment completed for orderId={}", command.orderId());
    }

    @Override
    public void complete(String orderId) {
        updateStatus(orderId,PaymentStatus.COMPLETED);
    }

    @Override
    public void fail(String orderId) {
        updateStatus(orderId,PaymentStatus.FAILED);
    }


    @Override
    public void refund(String orderId) {
        updateStatus(orderId,PaymentStatus.REFUNDED);
    }

    private void updateStatus(String orderId, PaymentStatus status) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new PaymentNotFoundException(orderId));
        payment.changeStatus(status);
        paymentRepository.save(payment);
    }

}