package org.booking.hotelinventory.exception.custom;


import org.booking.hotelinventory.exception.dto.ErrorCode;

public class HotelNotFoundException extends BaseException {

    public HotelNotFoundException(String id) {
        super("Order not found with id: " + id, ErrorCode.HOTEL_NOT_FOUND);
    }
}
