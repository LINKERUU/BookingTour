package org.booking.orderservice.exception.custom;


import org.booking.orderservice.exception.dto.ErrorCode;

public class NoAvailableRoomsException extends BaseException {
    public NoAvailableRoomsException(String id) {
        super("No available seats for id:" + id, ErrorCode.NO_AVAILABLE_ROOMS);
    }
}

