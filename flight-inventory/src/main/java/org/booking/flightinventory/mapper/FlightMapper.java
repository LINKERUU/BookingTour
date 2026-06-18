package org.booking.flightinventory.mapper;

import org.booking.flightinventory.dto.FlightRequest;
import org.booking.flightinventory.dto.FlightResponse;
import org.booking.flightinventory.model.Flight;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface FlightMapper {

    @Mapping(target = "id", ignore = true)
    Flight toFlight(FlightRequest request);

    FlightResponse toResponse(Flight flight);
}
