package org.booking.orderservice.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.booking.orderservice.model.enums.OrderStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Document(collection = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    private String id;
    private String userId;
    private String flightId;
    private String hotelId;
    private OrderStatus status;
    private BigDecimal amount;
    private LocalDateTime createdAt;


    public Order(String userId, String flightId, String hotelId) {
        this.userId = userId;
        this.flightId = flightId;
        this.hotelId = hotelId;
        this.status = OrderStatus.PENDING;
        this.amount = BigDecimal.ZERO;
        this.createdAt = LocalDateTime.now();
    }

    public void changeUserId(String userId) {
        this.userId = userId;
    }

    public void changeFlightId(String flightId) {
        this.flightId = flightId;
    }

    public void changeHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public void changeStatus(OrderStatus status) {
        this.status = status;
    }

    public void changeAmount(BigDecimal amount) {
        this.amount = amount;
    }
}



