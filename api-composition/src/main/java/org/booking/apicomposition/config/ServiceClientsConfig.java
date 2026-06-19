package org.booking.apicomposition.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@RequiredArgsConstructor
public class ServiceClientsConfig {

    @Value("${services.order-service}")
    private String orderServiceUrl;

    @Value("${services.payment-service}")
    private String paymentServiceUrl;

    @Value("${services.flight-service}")
    private String flightServiceUrl;

    @Value("${services.hotel-service}")
    private String hotelServiceUrl;

    @Bean("orderClient")
    public RestClient orderClient() {
        return RestClient.builder().baseUrl(orderServiceUrl).build();
    }

    @Bean("paymentClient")
    public RestClient paymentClient() {
        return RestClient.builder().baseUrl(paymentServiceUrl).build();
    }

    @Bean("flightClient")
    public RestClient flightClient() {
        return RestClient.builder().baseUrl(flightServiceUrl).build();
    }

    @Bean("hotelClient")
    public RestClient hotelClient() {
        return RestClient.builder().baseUrl(hotelServiceUrl).build();
    }

    @Bean
    public Executor compositionExecutor() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }
}

