package org.booking.bookingui.dto;

public record OrderRequest(
        String flightId,
        String hotelId
) {
}