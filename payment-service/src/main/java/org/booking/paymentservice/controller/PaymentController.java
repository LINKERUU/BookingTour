package org.booking.paymentservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.booking.paymentservice.dto.PaymentPatchRequest;
import org.booking.paymentservice.dto.PaymentRequest;
import org.booking.paymentservice.dto.PaymentResponse;
import org.booking.paymentservice.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final String ID = "/{id}";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponse createPayment(@Valid @RequestBody PaymentRequest request) {
        return paymentService.processPayment(request);
    }

    @GetMapping(ID)
    @ResponseStatus(HttpStatus.OK)
    public PaymentResponse getPaymentById(@PathVariable String id) {
        return paymentService.getPaymentById(id);
    }

    @PatchMapping(ID)
    public PaymentResponse updatePaymentById(@PathVariable String id, @Valid @RequestBody PaymentPatchRequest request) {
        return paymentService.updatePayment(id, request);
    }

    @DeleteMapping(ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePaymentById(@PathVariable String id) {
        paymentService.deletePaymentById(id);
    }

    @PostMapping(ID + "/refund")
    @ResponseStatus(HttpStatus.OK)
    public PaymentResponse refundPayment(@PathVariable String id) {
        return paymentService.cancelPayment(id);
    }

    @GetMapping("/order/{orderId}")
    public PaymentResponse getByOrderId(@PathVariable String orderId) {
        return paymentService.getByOrderId(orderId);
    }
}
