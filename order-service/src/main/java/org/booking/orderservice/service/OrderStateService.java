package org.booking.orderservice.service;

import org.booking.orderservice.model.Order;
import org.booking.orderservice.model.enums.OrderStatus;

import java.math.BigDecimal;

public interface OrderStateService {

    Order updateOrder(String orderId, BigDecimal price, OrderStatus expectedStatus, OrderStatus newStatus);

    Order cancel(String orderId, String reason);

    void updateOrderStatus(String orderId, OrderStatus newStatus);

    Order getOrder(String orderId);
}
