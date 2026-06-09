package org.booking.hotelinventory.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record HotelPatchRequest(

        @Size(min = 2, max = 100, message = "Hotel name must be between 2 and 100 characters")
        String hotelName,

        @Size(min = 2, max = 100, message = "City must be between 2 and 100 characters")
        String city,

        @PositiveOrZero(message = "Available rooms must be positive or zero")
        Integer availableRooms,

        @Positive(message = "Price per night must be positive")
        BigDecimal pricePerNight

) {
}
