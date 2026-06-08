package org.booking.orderservice.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.booking.orderservice.model.enums.OrderStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Document(collection = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    private String id;
    private Long userId;
    private Long flightId;
    private Long hotelId;
    private OrderStatus status;
    private LocalDateTime createdAt;

    public Order(Long userId, Long flightId, Long hotelId) {
        this.userId = userId;
        this.flightId = flightId;
        this.hotelId = hotelId;
        this.status = OrderStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public void changeUserId(Long userId) {
        this.userId = userId;
    }

    public void changeFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public void changeHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public void changeStatus(OrderStatus status) {
        this.status = status;
    }
}



