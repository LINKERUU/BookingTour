package org.booking.orderservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.booking.orderservice.dto.OrderPatchRequest;
import org.booking.orderservice.dto.OrderRequest;
import org.booking.orderservice.dto.OrderResponse;
import org.booking.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private static final String ID = "/{id}";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@Valid @RequestBody OrderRequest orderRequest,
                                     @RequestHeader("X-User-Id") String userId) {
        return orderService.createOrder(orderRequest, userId);
    }

    @GetMapping(ID)
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse findOrderById(@PathVariable String id) {
        return orderService.getOrderById(id);
    }

    @PatchMapping(ID)
    public OrderResponse updateOrder(@PathVariable String id, @Valid @RequestBody OrderPatchRequest orderRequest) {
        return orderService.updateOrder(id, orderRequest);
    }

    @DeleteMapping(ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrderById(@PathVariable String id) {
        orderService.deleteOrder(id);
    }
}
