package org.booking.paymentservice.model;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.booking.paymentservice.model.enums.PaymentStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Document(collection = "payments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id
    private String id;
    private String orderId;
    private String userId;
    private BigDecimal amount;
    private PaymentStatus status;
    private LocalDateTime createdAt;

    public Payment(String orderId, String userId, BigDecimal amount) {
        this.orderId = orderId;
        this.userId = userId;
        this.amount = amount;
        this.status = PaymentStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public void changeOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void changeUserId(String userId) {
        this.userId = userId;
    }

    public void changeAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void changeStatus(PaymentStatus paymentStatus) {
        this.status = paymentStatus;
    }

}
