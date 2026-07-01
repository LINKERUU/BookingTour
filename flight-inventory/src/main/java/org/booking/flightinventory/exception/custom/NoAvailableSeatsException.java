package org.booking.flightinventory.exception.custom;

import org.booking.flightinventory.exception.dto.ErrorCode;

public class NoAvailableSeatsException extends BaseException {
    public NoAvailableSeatsException() {
        super("Нет доступных мест в самолете", ErrorCode.NO_AVAILABLE_SEATS);
    }
}

