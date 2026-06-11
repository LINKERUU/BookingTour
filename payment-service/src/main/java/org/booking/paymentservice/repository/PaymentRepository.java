package org.booking.paymentservice.repository;

import org.booking.paymentservice.model.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PaymentRepository extends MongoRepository<Payment, String> {
    Optional<Payment> findByOrderId(String s);

    boolean existsByOrderId(String orderId);
}
