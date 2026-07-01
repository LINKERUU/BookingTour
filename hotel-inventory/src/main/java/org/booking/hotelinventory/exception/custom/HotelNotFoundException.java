package org.booking.hotelinventory.exception.custom;


import org.booking.hotelinventory.exception.dto.ErrorCode;

public class HotelNotFoundException extends BaseException {

    public HotelNotFoundException() {
        super("Отель не найден", ErrorCode.HOTEL_NOT_FOUND);
    }
}
