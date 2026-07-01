package org.booking.hotelinventory.exception.custom;

import org.booking.hotelinventory.exception.dto.ErrorCode;

public class NoAvailableRoomsException extends BaseException {
    public NoAvailableRoomsException() {
        super("Нет свободных комнат в отеле", ErrorCode.NO_AVAILABLE_ROOMS);
    }
}
