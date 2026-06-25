package org.booking.bookingui.service.impl;

import lombok.RequiredArgsConstructor;
import org.booking.bookingui.service.UtilsService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UtilsServiceImpl implements UtilsService {

    private final RestClient gatewayClient;

    @Override
    public <T> T getRequest(String uri, String id, Class<T> type) {
        return gatewayClient
                .get()
                .uri(uri, id)
                .retrieve()
                .body(type);
    }

    @Override
    public <T> List<T> getListRequest(String uri, ParameterizedTypeReference<List<T>> type) {
        return gatewayClient.get()
                .uri(uri)
                .retrieve()
                .body(type);
    }

    @Override
    public <T, R> R post(String uri, T body, Class<R> type) {
        return gatewayClient.post()
                .uri(uri)
                .body(body)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(type);
    }
}
