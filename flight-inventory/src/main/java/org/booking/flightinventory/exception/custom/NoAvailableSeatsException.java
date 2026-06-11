package org.booking.flightinventory.exception.custom;

import org.booking.flightinventory.exception.dto.ErrorCode;

public class NoAvailableSeatsException extends BaseException {
    public NoAvailableSeatsException(String id) {
        super("No available seats for id:" + id, ErrorCode.NO_AVAILABLE_SEATS);
    }
}

