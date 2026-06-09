package org.booking.flightinventory.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.booking.flightinventory.dto.FlightPatchRequest;
import org.booking.flightinventory.dto.FlightRequest;
import org.booking.flightinventory.dto.FlightResponse;
import org.booking.flightinventory.service.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;
    private final String ID = "/{id}";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FlightResponse createFlight(@Valid @RequestBody FlightRequest request) {
       return flightService.createFlight(request);
    }

    @GetMapping(ID)
    @ResponseStatus(HttpStatus.OK)
    public FlightResponse getFlightById(@PathVariable String id)
    {
        return flightService.getFlightById(id);
    }

    @PatchMapping(ID)
    public FlightResponse updateFlightById(@PathVariable String id ,@Valid @RequestBody FlightPatchRequest request) {
        return flightService.updateFlight(id,request);
    }

    @DeleteMapping(ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFlightById(@PathVariable String id)
    {
        flightService.deleteFlightById(id);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<FlightResponse> searchFlightByArrivalAndDeparture(@RequestParam String arrival, @RequestParam String departure)
    {
        return flightService.searchFlights(arrival,departure);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FlightResponse> searchFlights()
    {
        return flightService.allFlights();
    }
}
