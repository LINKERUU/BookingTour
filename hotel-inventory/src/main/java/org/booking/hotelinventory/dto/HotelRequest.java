package org.booking.hotelinventory.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record HotelRequest(
        @NotBlank(message = "Hotel name cannot be blank")
        @Size(min = 2, max = 100, message = "Hotel name must be between 2 and 100 characters")
        String hotelName,

        @NotBlank(message = "City cannot be blank")
        @Size(min = 2, max = 100, message = "City must be between 2 and 100 characters")
        String city,

        @NotNull(message = "Available rooms count is required")
        @PositiveOrZero(message = "Available rooms must be positive or zero")
        Integer availableRooms,

        @NotNull(message = "Price per night cannot be null")
        @Positive(message = "Price per night must be positive")
        BigDecimal pricePerNight

) {
}
