package org.booking.flightinventory.service;

import org.booking.flightinventory.dto.FlightPatchRequest;
import org.booking.flightinventory.dto.FlightRequest;
import org.booking.flightinventory.dto.FlightResponse;
import org.booking.flightinventory.model.Flight;

import java.util.List;

public interface FlightService {

    FlightResponse createFlight(FlightRequest request);

    FlightResponse getFlightById(String id);

    FlightResponse updateFlight(String id, FlightPatchRequest request);

    List<FlightResponse> searchFlights(String arrival, String departure);

    void deleteFlightById(String id);

    List<FlightResponse> allFlights();

}
