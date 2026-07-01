package org.booking.flightinventory.exception.custom;


import org.booking.flightinventory.exception.dto.ErrorCode;

public class FlightNotFoundException extends BaseException {

    public FlightNotFoundException(){
        super("Рейс не найден", ErrorCode.FLIGHT_NOT_FOUND);
    }
}
