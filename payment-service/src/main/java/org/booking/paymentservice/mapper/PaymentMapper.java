package org.booking.paymentservice.mapper;

import org.booking.paymentservice.dto.PaymentRequest;
import org.booking.paymentservice.dto.PaymentResponse;
import org.booking.paymentservice.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    Payment toPayment(PaymentRequest request);

    PaymentResponse toResponse(Payment flight);
}
