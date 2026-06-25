package org.booking.bookingui.dto;
import java.math.BigDecimal;

public record HotelDto(
        String id,
        String name,
        String city,
        Integer availableRooms,
        BigDecimal pricePerNight
) {}