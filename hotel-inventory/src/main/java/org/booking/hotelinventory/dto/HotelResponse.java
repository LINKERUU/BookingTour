package org.booking.hotelinventory.dto;

import java.math.BigDecimal;

public record HotelResponse(
        String id,
        String name,
        String city,
        Integer availableRooms,
        BigDecimal pricePerNight
) {
}
