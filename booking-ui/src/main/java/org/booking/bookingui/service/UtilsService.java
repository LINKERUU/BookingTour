package org.booking.bookingui.service;

import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public interface UtilsService {
    <T> T getRequest(String uri, String id, Class<T> type);
    <T> List<T> getListRequest(String uri, ParameterizedTypeReference<List<T>> type);
    <T, R> R post(String uri, T body, Class<R> type);
}
