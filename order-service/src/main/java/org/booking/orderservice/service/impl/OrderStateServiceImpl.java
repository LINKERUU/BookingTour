package org.booking.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.booking.orderservice.exception.custom.OrderNotFoundException;
import org.booking.orderservice.model.Order;
import org.booking.orderservice.model.enums.OrderStatus;
import org.booking.orderservice.repository.OrderRepository;
import org.booking.orderservice.service.OrderStateService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class OrderStateServiceImpl implements OrderStateService {

    private final OrderRepository orderRepository;

    @Override
    public Order updateOrder(String orderId, BigDecimal price, OrderStatus expectedStatus, OrderStatus newStatus) {
        Order order = getOrder(orderId);

        if (!order.getStatus().canTransition(newStatus)) {
            return order;
        }

        order.changeAmount(order.getAmount().add(price));
        order.changeStatus(newStatus);

        orderRepository.save(order);

        return order;
    }

    @Override
    public void updateOrderStatus(String orderId, OrderStatus newStatus) {

        Order order = getOrder(orderId);
        order.changeStatus(newStatus);
        orderRepository.save(order);

    }

    @Override
    public Order cancel(String orderId, String reason) {
        Order order = getOrder(orderId);

        if (order.getStatus() == OrderStatus.CANCELLED) {
            return order;
        }

        order.changeReason(reason);
        order.changeStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);

        return order;
    }

    @Override
    public Order getOrder(String orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
    }


}
