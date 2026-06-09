package org.booking.flightinventory.exception.custom;


import org.booking.flightinventory.exception.dto.ErrorCode;

public class FlightNotFoundException extends BaseException {

    public FlightNotFoundException(String id){
        super("Order not found with id: " + id , ErrorCode.FLIGHT_NOT_FOUND);
    }
}
