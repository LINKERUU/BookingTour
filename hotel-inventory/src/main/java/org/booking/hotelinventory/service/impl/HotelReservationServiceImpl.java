package org.booking.hotelinventory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ReservationResult reserve(String HotelId, BigDecimal amount) {
        return hotelRepository.findById(HotelId)
                .map(hotel -> {
                    if (hotel.getAvailableRooms() <= 0) {
                        log.warn("No seats available HotelId={}", HotelId);
                        return ReservationResult.failure("No available seats for Hotel: " + HotelId);
                    }
                    hotel.reserveRoom();
                    hotelRepository.save(hotel);
                    log.info("Seat reserved HotelId={}", HotelId);
                    return ReservationResult.success(amount.add(hotel.getPricePerNight()));
                })
                .orElseGet(() -> {
                    log.warn("Hotel not found HotelId={}", HotelId);
                    return ReservationResult.failure("Hotel not found: " + HotelId);
                });
    }

    @Override
    public void cancel(BookingCommand command) {
        hotelRepository.findById(command.hotelId()).ifPresent(hotel -> {
            hotel.releaseRoom();
            hotelRepository.save(hotel);
            log.info("Seat released orderId={}", command.orderId());
        });
    }
}
