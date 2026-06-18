package org.booking.orderservice.dto;

import jakarta.validation.constraints.NotBlank;

public record OrderRequest(

        @NotBlank(message = "Flight ID cannot be blank")
        String flightId,

        @NotBlank(message = "Hotel ID cannot be blank")
        String hotelId
) {
}
