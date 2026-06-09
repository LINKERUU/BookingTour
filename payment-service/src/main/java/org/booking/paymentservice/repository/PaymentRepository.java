package org.booking.paymentservice.repository;

import org.booking.paymentservice.model.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PaymentRepository extends MongoRepository<Payment, String> {
}
