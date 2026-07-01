package org.booking.flightinventory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.booking.flightinventory.dto.FlightPatchRequest;
import org.booking.flightinventory.dto.FlightRequest;
import org.booking.flightinventory.dto.FlightResponse;
import org.booking.flightinventory.exception.custom.FlightNotFoundException;
import org.booking.flightinventory.mapper.FlightMapper;
import org.booking.flightinventory.model.Flight;
import org.booking.flightinventory.repository.FlightRepository;
import org.booking.flightinventory.service.FlightService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;

    @Override
    public FlightResponse createFlight(FlightRequest request) {
        Flight flight = flightMapper.toFlight(request);
        flightRepository.save(flight);
        log.info("Flight with id {} has been created", flight.getId());
        return flightMapper.toResponse(flight);
    }

    @Override
    public FlightResponse getFlightById(String id) {
        return flightMapper.toResponse(getExistingFlight(id));
    }

    @Override
    public FlightResponse updateFlight(String id, FlightPatchRequest request) {
        Flight flight = getExistingFlight(id);

        applyUpdate(flight, request);

        flightRepository.save(flight);

        log.info("Flight updated with ID: {}", id);

        return flightMapper.toResponse(flight);
    }

    @Override
    public List<FlightResponse> searchFlights(String arrival, String departure) {
        return flightRepository.findByArrivalToAndDepartureFrom(arrival, departure)
                .stream()
                .map(flightMapper::toResponse)
                .toList();
    }

    @Override
    public List<FlightResponse> allFlights() {
        return flightRepository.findAll().stream()
                .map(flightMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteFlightById(String id) {

        getExistingFlight(id);

        log.info("Flight with id {} has been deleted", id);

        flightRepository.deleteById(id);
    }

    private Flight getExistingFlight(String id) {
        return flightRepository.findById(id).orElseThrow(FlightNotFoundException::new);
    }

    private void applyUpdate(Flight flight, FlightPatchRequest request) {
        Optional.ofNullable(request.flightNumber()).ifPresent(flight::changeFlightNumber);
        Optional.ofNullable(request.arrivalTo()).ifPresent(flight::changeArrivalTo);
        Optional.ofNullable(request.departureFrom()).ifPresent(flight::changeDepartureFrom);
        Optional.ofNullable(request.departureTime()).ifPresent(flight::changeDepartureTime);
        Optional.ofNullable(request.availableSeats()).ifPresent(flight::changeAvailableSeats);
        Optional.ofNullable(request.price()).ifPresent(flight::changePrice);
    }
}
