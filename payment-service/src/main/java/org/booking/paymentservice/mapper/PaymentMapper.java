package org.booking.paymentservice.mapper;

import org.booking.paymentservice.dto.PaymentRequest;
import org.booking.paymentservice.dto.PaymentResponse;
import org.booking.paymentservice.model.Payment;
import org.booking.sharedlib.messaging.event.BookingCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    Payment toPayment(PaymentRequest request,String userId);

    PaymentResponse toResponse(Payment flight);

    Payment toPayment(BookingCommand command);
}
