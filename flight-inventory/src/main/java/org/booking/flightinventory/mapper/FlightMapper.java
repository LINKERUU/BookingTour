package org.booking.flightinventory.mapper;

import org.booking.flightinventory.dto.FlightRequest;
import org.booking.flightinventory.dto.FlightResponse;
import org.booking.flightinventory.model.Flight;
import org.booking.sharedlib.messaging.event.BookingCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FlightMapper {

    @Mapping(target = "id", ignore = true)
    Flight toFlight(FlightRequest request);

//    @Mapping(target = "id", source = "id")
//    BookingCommand toBookingCommand(Order order);

    FlightResponse toResponse(Flight flight);
}
