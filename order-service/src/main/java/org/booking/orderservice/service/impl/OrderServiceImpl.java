package org.booking.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.booking.orderservice.dto.OrderPatchRequest;
import org.booking.orderservice.dto.OrderRequest;
import org.booking.orderservice.dto.OrderResponse;
import org.booking.orderservice.exception.custom.OrderNotFoundException;
import org.booking.orderservice.mapper.OrderMapper;
import org.booking.orderservice.messaging.SagaOrchestrator;
import org.booking.orderservice.model.Order;
import org.booking.orderservice.repository.OrderRepository;
import org.booking.orderservice.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final SagaOrchestrator sagaOrchestrator;

    @Override
    public OrderResponse createOrder(OrderRequest request, String userId) {
        Order order = orderMapper.toOrder(request, userId);
        orderRepository.save(order);

        sagaOrchestrator.startSaga(orderMapper.toBookingCommand(order));

        log.info("Order created with ID: {}", order.getId());

        return orderMapper.toResponse(order);
    }

    @Override
    public OrderResponse getOrderById(String id) {
        log.info("Get order by ID: {}", id);

        return orderMapper.toResponse(getExistingOrder(id));
    }

    @Override
    public OrderResponse updateOrder(String id, OrderPatchRequest request) {

        Order order = getExistingOrder(id);

        applyUpdate(order, request);

        orderRepository.save(order);

        log.info("Order updated with ID: {}", id);

        return orderMapper.toResponse(order);
    }

    @Override
    public void deleteOrder(String id) {

        orderRepository.delete(getExistingOrder(id));
        log.info("Delete order by ID: {}", id);

    }

    private Order getExistingOrder(String id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    private void applyUpdate(Order order, OrderPatchRequest request) {
        Optional.ofNullable(request.flightId()).ifPresent(order::changeFlightId);
        Optional.ofNullable(request.hotelId()).ifPresent(order::changeHotelId);
    }
}
