package org.booking.paymentservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.booking.paymentservice.dto.PaymentPatchRequest;
import org.booking.paymentservice.dto.PaymentRequest;
import org.booking.paymentservice.dto.PaymentResponse;
import org.booking.paymentservice.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@EnableMethodSecurity
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final String ID = "/{id}";

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponse createPayment(@Valid @RequestBody PaymentRequest request,
                                        @RequestHeader("X-User-Id") String userId) {
        return paymentService.processPayment(request, userId);
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
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponse refundPayment(@PathVariable String id) {
        return paymentService.cancelPayment(id);
    }

    @GetMapping("/order/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public PaymentResponse getByOrderId(@PathVariable String orderId) {
        return paymentService.getByOrderId(orderId);
    }
}
