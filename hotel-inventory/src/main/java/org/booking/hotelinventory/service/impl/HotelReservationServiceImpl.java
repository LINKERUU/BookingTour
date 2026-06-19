package org.booking.hotelinventory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.booking.hotelinventory.exception.custom.HotelNotFoundException;
import org.booking.hotelinventory.model.Hotel;
import org.booking.hotelinventory.repository.HotelRepository;
import org.booking.hotelinventory.service.HotelReservationService;
import org.booking.sharedlib.messaging.event.BookingCommand;
import org.booking.sharedlib.messaging.result.ReservationResult;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotelReservationServiceImpl implements HotelReservationService {

    private final HotelRepository hotelRepository;

    @Override
    public ReservationResult reserve(String hotelId, BigDecimal amount) {

        Hotel hotel = getExistHotel(hotelId);

        if (hotel.getAvailableRooms() <= 0) {
            log.warn("No rooms available hotelId={}", hotelId);
            return ReservationResult.failure("No available rooms for Hotel: " + hotelId);
        }

        hotel.reserveRoom();
        hotelRepository.save(hotel);

        return ReservationResult.success(amount.add(hotel.getPricePerNight()));
    }

    @Override
    public void cancel(BookingCommand command) {
        Hotel hotel = getExistHotel(command.hotelId());

        hotel.releaseRoom();
        hotelRepository.save(hotel);

        log.info("Room released orderId={}", command.orderId());
    }

    private Hotel getExistHotel(String hotelId) {
        return hotelRepository.findById(hotelId).orElseThrow(
                () -> new HotelNotFoundException(hotelId));

    }
}
