package org.booking.hotelinventory.exception.custom;

import org.booking.hotelinventory.exception.dto.ErrorCode;

public class NoAvailableRoomsException extends BaseException {
    public NoAvailableRoomsException(String id) {
        super("No available rooms for id:" + id, ErrorCode.NO_AVAILABLE_ROOMS);
    }
}
