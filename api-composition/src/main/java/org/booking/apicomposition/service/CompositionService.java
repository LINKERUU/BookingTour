package org.booking.apicomposition.service;

import org.booking.apicomposition.dto.BookingDetailsResponse;

public interface CompositionService {

    BookingDetailsResponse getBookingDetails(String orderId);
}
