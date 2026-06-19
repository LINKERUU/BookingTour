package org.booking.apicomposition.service;

import org.booking.apicomposition.dto.BookingDetailsResponse;
import org.booking.apicomposition.dto.OrderInfo;

public interface CompositionService {

    BookingDetailsResponse getBookingDetails(String orderId);

    OrderInfo fetchOrderDetails(String orderId);
}
