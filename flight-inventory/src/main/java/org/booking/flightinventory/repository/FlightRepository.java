package org.booking.flightinventory.repository;

import org.booking.flightinventory.dto.FlightResponse;
import org.booking.flightinventory.model.Flight;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends MongoRepository<Flight,String> {

    List<Flight> findByArrivalToAndDepartureFrom(String arrival, String departure);
}
