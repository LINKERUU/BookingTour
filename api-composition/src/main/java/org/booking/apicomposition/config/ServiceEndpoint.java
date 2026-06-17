package org.booking.apicomposition.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServiceEndpoint {

    ORDER(
            "order-service",
            "/api/orders/{id}"
    ),

    FLIGHT(
            "flight-service",
            "/api/flights/{id}"
    ),

    HOTEL(
            "hotel-service",
            "/api/hotels/{id}"
    ),

    PAYMENT(
            "payment-service",
            "/api/payments/order/{id}"
    );

    private final String serviceName;
    private final String uri;
}
