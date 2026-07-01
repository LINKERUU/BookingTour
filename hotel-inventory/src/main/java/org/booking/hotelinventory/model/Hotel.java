package org.booking.hotelinventory.model;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@Document(collection = "hotels")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hotel {

    @Version
    private Long version;

    @Id
    private String id;
    private String name;
    private String city;
    private Integer availableRooms;
    private BigDecimal pricePerNight;

    public Hotel(String name, String city, Integer availableRooms, BigDecimal pricePerNight) {
        this.name = name;
        this.city = city;
        this.availableRooms = availableRooms;
        this.pricePerNight = pricePerNight;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeCity(String city) {
        this.city = city;
    }

    public void changeAvailableRooms(Integer availableRooms) {
        this.availableRooms = availableRooms;
    }

    public void changePricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public void reserveRoom() {
        this.availableRooms--;
    }

    public void releaseRoom() {
        this.availableRooms++;
    }
}
