package org.booking.apicomposition.mapper;

import org.booking.apicomposition.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompositionMapper {

    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "order.status", target = "status")
    @Mapping(source = "order.amount", target = "totalAmount")
    BookingDetailsResponse toDetailsResponse(OrderInfo order, FlightInfo flight, HotelInfo hotel, PaymentInfo payment);
}
