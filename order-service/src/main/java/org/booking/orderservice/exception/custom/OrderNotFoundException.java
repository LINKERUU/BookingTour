package org.booking.orderservice.exception.custom;

import org.booking.orderservice.exception.dto.ErrorCode;

public class OrderNotFoundException extends BaseException {

    public OrderNotFoundException(String id) {
        super("Order not found with id: " + id, ErrorCode.ORDER_NOT_FOUND);
    }
}
