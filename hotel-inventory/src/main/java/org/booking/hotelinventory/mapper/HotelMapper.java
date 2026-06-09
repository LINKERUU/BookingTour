package org.booking.hotelinventory.mapper;


import org.booking.hotelinventory.dto.HotelRequest;
import org.booking.hotelinventory.dto.HotelResponse;
import org.booking.hotelinventory.model.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HotelMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name" , source = "hotelName")
    Hotel toHotel(HotelRequest request);

    HotelResponse toResponse(Hotel flight);
}
