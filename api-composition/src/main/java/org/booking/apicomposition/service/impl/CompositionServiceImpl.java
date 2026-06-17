package org.booking.apicomposition.service.impl;

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
    private final Map<ServiceEndpoint,RestClient> clients;

    public CompositionServiceImpl(
            CompositionMapper compositionMapper,
            Executor compositionExecutor,
            @Qualifier("orderClient") RestClient orderClient,
            @Qualifier("paymentClient") RestClient paymentClient,
            @Qualifier("hotelClient") RestClient hotelClient,
            @Qualifier("flightClient") RestClient flightClient
    ) {
        this.compositionMapper = compositionMapper;
        this.compositionExecutor = compositionExecutor;
        this.clients = Map.of(
                ServiceEndpoint.ORDER, orderClient,
                ServiceEndpoint.FLIGHT, flightClient,
                ServiceEndpoint.HOTEL, hotelClient,
                ServiceEndpoint.PAYMENT, paymentClient
        );
    }

    @Override
    public BookingDetailsResponse getBookingDetails(String orderId) {

        log.info("Composing booking details for orderId:{}", orderId);

        OrderInfo order = fetchOrder(orderId);

        CompletableFuture<FlightInfo> flightFuture = fetchAsyncService(ServiceEndpoint.FLIGHT, order.flightId(), FlightInfo.class);
        CompletableFuture<HotelInfo> hotelFuture = fetchAsyncService(ServiceEndpoint.HOTEL, order.hotelId(), HotelInfo.class);
        CompletableFuture<PaymentInfo> paymentFuture = fetchAsyncService(ServiceEndpoint.PAYMENT, orderId, PaymentInfo.class);

        CompletableFuture.allOf(
                flightFuture,
                hotelFuture,
                paymentFuture
        ).join();

        FlightInfo flight = flightFuture.join();
        HotelInfo hotel = hotelFuture.join();
        PaymentInfo payment = paymentFuture.join();

        return compositionMapper.toDetailsResponse(order, flight, hotel, payment);
    }

    @Override
    public OrderInfo fetchOrder(String orderId) {
        return fetchService(ServiceEndpoint.ORDER, orderId, clients.get(ServiceEndpoint.ORDER), OrderInfo.class);
    }


    private <T> CompletableFuture<T> fetchAsyncService(ServiceEndpoint serviceEndpoint, String entityId, Class<T> responseType) {
        return CompletableFuture.supplyAsync(() -> fetchService(
                serviceEndpoint, entityId, clients.get(serviceEndpoint), responseType),compositionExecutor
        );
    }


    private <T> T fetchService(ServiceEndpoint service, String entityId, RestClient client, Class<T> responseType) {
        log.info("Fetching {} id={}", service.getServiceName(), entityId);

        return client.get()
                .uri(service.getUri(), entityId)
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        (_, _) -> {
                            throw new ClientServiceUnavailableException(service.getServiceName(), entityId, null);
                        }
                )
                .body(responseType);
    }

}
