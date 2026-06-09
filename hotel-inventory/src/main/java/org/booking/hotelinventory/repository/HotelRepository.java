package org.booking.hotelinventory.repository;

import org.booking.hotelinventory.model.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HotelRepository extends MongoRepository<Hotel, String> {

    Hotel findByName(String name);
}
