package org.booking.flightinventory.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Document(collection = "flights")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Flight {

    @Id
    private String id;
    private String flightNumber;
    private String departureFrom;
    private String arrivalTo;
    private LocalDateTime departureTime;
    private Integer availableSeats;
    private BigDecimal price;

    public Flight(String flightNumber, String departureFrom, String arrivalTo, Integer availableSeats, LocalDateTime departureTime, BigDecimal price) {
        this.flightNumber = flightNumber;
        this.departureFrom = departureFrom;
        this.arrivalTo = arrivalTo;
        this.availableSeats = availableSeats;
        this.departureTime = departureTime;
        this.price = price;
    }

    public void changeFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void changeDepartureFrom(String departureFrom) {
        this.departureFrom = departureFrom;
    }

    public void changeArrivalTo(String arrivalTo) {
        this.arrivalTo = arrivalTo;
    }

    public void changeAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public void changeDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public void changePrice(BigDecimal price) {
        this.price = price;
    }

    public void reserveSeat() {
        this.availableSeats--;
    }

    public void releaseSeat() {
        this.availableSeats++;
    }

}
