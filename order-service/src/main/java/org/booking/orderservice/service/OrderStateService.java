package org.booking.orderservice.service;

import org.booking.orderservice.model.Order;
import org.booking.orderservice.model.enums.OrderStatus;

import java.math.BigDecimal;

public interface OrderStateService {

    Order getOrder(String orderId);

    void changeStatus(String orderId, OrderStatus orderStatus);

    void confirm(String orderId, BigDecimal amount);

    void cancel(String orderId);
}
