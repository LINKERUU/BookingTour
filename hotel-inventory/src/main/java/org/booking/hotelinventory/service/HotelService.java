package org.booking.hotelinventory.service;


import org.booking.hotelinventory.dto.HotelPatchRequest;
import org.booking.hotelinventory.dto.HotelRequest;
import org.booking.hotelinventory.dto.HotelResponse;

import java.util.List;

public interface HotelService {

    HotelResponse createHotel(HotelRequest request);

    HotelResponse getHotelById(String id);

    HotelResponse updateHotel(String id, HotelPatchRequest request);

    HotelResponse searchHotels(String name);

    void deleteHotelById(String id);

    List<HotelResponse> allHotels();

}
