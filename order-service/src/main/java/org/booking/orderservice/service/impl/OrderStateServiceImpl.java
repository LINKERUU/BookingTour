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
    public Order getOrder(String orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    @Override
    public void changeStatus(String orderId, BigDecimal amount, OrderStatus orderStatus) {
        Order order = getOrder(orderId);
        order.changeAmount(amount);
        order.changeStatus(orderStatus);
        orderRepository.save(order);
    }

    @Override
    public void confirm(String orderId, BigDecimal amount) {

        Order order = getOrder(orderId);
        order.changeAmount(amount);
        order.changeStatus(OrderStatus.CONFIRMED);

        orderRepository.save(order);
    }

    @Override
    public void cancel(String orderId, String reason) {
        Order order = getOrder(orderId);

        order.changeReason(reason);
        order.changeStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);
    }
}
