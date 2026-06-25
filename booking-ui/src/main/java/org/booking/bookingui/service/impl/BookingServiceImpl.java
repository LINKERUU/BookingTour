package org.booking.bookingui.service.impl;

import lombok.RequiredArgsConstructor;
import org.booking.bookingui.dto.*;
import org.booking.bookingui.service.BookingService;
import org.booking.bookingui.service.UtilsService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final UtilsService utilsService;

    @Override
    public List<FlightDto> getFlights() {
        return utilsService.getListRequest("/api/flights", new ParameterizedTypeReference<>() {});
    }

    @Override
    public List<HotelDto> getHotels() {
        return utilsService.getListRequest("/api/hotels", new ParameterizedTypeReference<>() {});
    }

    @Override
    public OrderResponse createOrder(OrderRequest request) {
        return utilsService.post("/api/orders", request, OrderResponse.class);
    }

    @Override
    public BookingDetailsDto getBookingDetails(String id) {
        return utilsService.getRequest("/api/booking/{id}", id, BookingDetailsDto.class);
    }

    @Override
    public OrderResponse getOrderStatus(String id) {
        return utilsService.getRequest("/api/orders/{id}", id, OrderResponse.class);
    }


}
