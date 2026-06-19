package org.booking.paymentservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.booking.paymentservice.dto.PaymentPatchRequest;
import org.booking.paymentservice.dto.PaymentRequest;
import org.booking.paymentservice.dto.PaymentResponse;
import org.booking.paymentservice.exception.custom.PaymentNotFoundException;
import org.booking.paymentservice.mapper.PaymentMapper;
import org.booking.paymentservice.model.Payment;
import org.booking.paymentservice.model.enums.PaymentStatus;
import org.booking.paymentservice.repository.PaymentRepository;
import org.booking.paymentservice.service.PaymentService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public PaymentResponse processPayment(PaymentRequest request, String userId) {
        Payment payment = paymentMapper.toPayment(request, userId);
        paymentRepository.save(payment);
        log.info("Payment with id {} has been created", payment.getId());
        return paymentMapper.toResponse(payment);
    }

    @Override
    public PaymentResponse getPaymentById(String id) {
        return paymentMapper.toResponse(getExistingPayment(id));
    }

    @Override
    public PaymentResponse updatePayment(String id, PaymentPatchRequest request) {
        Payment payment = getExistingPayment(id);
        applyUpdate(payment, request);

        log.info("Payment updated with ID: {}", id);

        return paymentMapper.toResponse(payment);
    }

    @Override
    public void deletePaymentById(String id) {

        getExistingPayment(id);

        log.info("Payment with id {} has been deleted", id);

        paymentRepository.deleteById(id);
    }

    @Override
    public PaymentResponse cancelPayment(String id) {
        Payment payment = getExistingPayment(id);

        if (payment.getStatus() == PaymentStatus.COMPLETED) {
            payment.changeStatus(PaymentStatus.REFUNDED);
            log.info("Payment with id {} has been refunded", id);
            paymentRepository.save(payment);
        }

        log.info("Payment with id {} couldn't be refunded", id);

        return paymentMapper.toResponse(payment);
    }

    @Override
    public PaymentResponse getByOrderId(String orderId){

        Payment payment = paymentRepository.findByOrderId(orderId).orElseThrow(() -> new PaymentNotFoundException(orderId));

        return paymentMapper.toResponse(payment);
    }

    private Payment getExistingPayment(String id) {
        return paymentRepository.findById(id).orElseThrow(() -> new PaymentNotFoundException(id));
    }

    private void applyUpdate(Payment payment, PaymentPatchRequest request) {
        Optional.ofNullable(request.orderId()).ifPresent(payment::changeOrderId);
        Optional.ofNullable(request.amount()).ifPresent(payment::changeAmount);
    }
}
