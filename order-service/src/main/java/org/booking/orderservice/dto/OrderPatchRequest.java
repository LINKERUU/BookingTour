package org.booking.orderservice.dto;

public record OrderPatchRequest(
        String flightId,
        String hotelId
) {
}
