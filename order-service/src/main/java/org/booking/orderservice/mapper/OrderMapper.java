package org.booking.orderservice.mapper;

import org.booking.orderservice.dto.OrderRequest;
import org.booking.orderservice.dto.OrderResponse;
import org.booking.orderservice.model.Order;
import org.booking.sharedlib.messaging.event.BookingCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Order toOrder(OrderRequest request, String userId);

    @Mapping(target = "orderId", source = "id")
    BookingCommand toBookingCommand(Order order);

    OrderResponse toResponse(Order order);
}
