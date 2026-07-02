package org.booking.bookingui.service;

import org.booking.bookingui.dto.*;
import java.util.List;

public interface BookingService {

    Object getFlights();
    List<HotelDto> getHotels();
    OrderResponse createOrder(OrderRequest request);
    BookingDetailsDto getBookingDetails(String id);
    OrderResponse getOrderStatus(String id);
    List<OrderResponse> getUserOrders();
}
