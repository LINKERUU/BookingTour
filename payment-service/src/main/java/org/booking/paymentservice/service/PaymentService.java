package org.booking.paymentservice.service;

import org.booking.paymentservice.dto.PaymentPatchRequest;
import org.booking.paymentservice.dto.PaymentRequest;
import org.booking.paymentservice.dto.PaymentResponse;

public interface PaymentService {

    PaymentResponse processPayment(PaymentRequest request);

    PaymentResponse getPaymentById(String id);

    PaymentResponse updatePayment(String id, PaymentPatchRequest request);

    void deletePaymentById(String id);

    PaymentResponse cancelPayment(String id);

    PaymentResponse getByOrderId(String orderId);
}
