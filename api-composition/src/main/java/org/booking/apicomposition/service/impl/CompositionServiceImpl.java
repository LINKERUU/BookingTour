package org.booking.apicomposition.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.booking.apicomposition.config.ServiceEndpoint;
import org.booking.apicomposition.dto.*;
import org.booking.apicomposition.exception.custom.ClientServiceUnavailableException;
import org.booking.apicomposition.mapper.CompositionMapper;
import org.booking.apicomposition.service.CompositionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Slf4j
@Service
public class CompositionServiceImpl implements CompositionService {

    private final CompositionMapper compositionMapper;
    private final Executor compositionExecutor;
    private final Map<ServiceEndpoint, RestClient> clients;
    private final HttpServletRequest servletRequest;

    public CompositionServiceImpl(
            CompositionMapper compositionMapper,
            Executor compositionExecutor,
            HttpServletRequest request,
            @Qualifier("orderClient") RestClient orderClient,
            @Qualifier("paymentClient") RestClient paymentClient,
            @Qualifier("hotelClient") RestClient hotelClient,
            @Qualifier("flightClient") RestClient flightClient
    ) {
        this.compositionMapper = compositionMapper;
        this.compositionExecutor = compositionExecutor;
        this.servletRequest = request;
        this.clients = Map.of(
                ServiceEndpoint.ORDER, orderClient,
                ServiceEndpoint.FLIGHT, flightClient,
                ServiceEndpoint.HOTEL, hotelClient,
                ServiceEndpoint.PAYMENT, paymentClient
        );

    }

    @Override
    public BookingDetailsResponse getBookingDetails(String orderId) {
        log.info("Composing booking details for orderId={}", orderId);

        Map<String, String> headers = extractHeaders();

        OrderInfo order = fetchOrder(orderId, headers);

        CompletableFuture<FlightInfo> flightFuture =
                fetchAsync(ServiceEndpoint.FLIGHT, order.flightId(), FlightInfo.class, headers);
        CompletableFuture<HotelInfo> hotelFuture =
                fetchAsync(ServiceEndpoint.HOTEL, order.hotelId(), HotelInfo.class, headers);
        CompletableFuture<PaymentInfo> paymentFuture =
                fetchAsync(ServiceEndpoint.PAYMENT, orderId, PaymentInfo.class, headers);

        CompletableFuture.allOf(flightFuture, hotelFuture, paymentFuture).join();

        return compositionMapper.toDetailsResponse(
                order,
                flightFuture.join(),
                hotelFuture.join(),
                paymentFuture.join()
        );
    }

    @Override
    public OrderInfo fetchOrderDetails(String orderId) {
        return fetchOrder(orderId, extractHeaders());
    }

    private Map<String, String> extractHeaders() {
        return Map.of(
                "X-User-Id", servletRequest.getHeader("X-User-Id"),
                "X-User-Email", servletRequest.getHeader("X-User-Email"),
                "X-User-Role",servletRequest.getHeader("X-User-Role")
        );
    }

    private OrderInfo fetchOrder(String orderId, Map<String, String> headers) {
        return fetch(ServiceEndpoint.ORDER, orderId, headers, OrderInfo.class);
    }

    private <T> CompletableFuture<T> fetchAsync(ServiceEndpoint endpoint, String id, Class<T> type, Map<String, String> headers) {
        return CompletableFuture.supplyAsync(() -> fetch(endpoint, id, headers, type), compositionExecutor);
    }

    private <T> T fetch(ServiceEndpoint endpoint, String id, Map<String, String> headers, Class<T> type) {
        log.info("Fetching {} id={}", endpoint.getServiceName(), id);

        return clients.get(endpoint)
                .get()
                .uri(endpoint.getUri(), id)
                .headers(h -> headers.forEach(h::add))
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        (_, _) -> {
                            throw new ClientServiceUnavailableException(endpoint.getServiceName(), id);
                        }
                ).body(type);
    }
}
