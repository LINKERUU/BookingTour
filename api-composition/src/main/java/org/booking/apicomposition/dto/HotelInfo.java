package org.booking.apicomposition.dto;

import java.math.BigDecimal;

public record HotelInfo(
        String id,
        String name,
        String city,
        BigDecimal pricePerNight
) {
}
