package org.booking.orderservice.service;

import org.booking.orderservice.dto.OrderPatchRequest;
import org.booking.orderservice.dto.OrderRequest;
import org.booking.orderservice.dto.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequest orderRequest, String userId);

    OrderResponse getOrderById(String id);

    OrderResponse updateOrder(String id, OrderPatchRequest orderRequest);

    void deleteOrder(String id);

    List<OrderResponse> getByUserId(String userId);
}
