package org.booking.hotelinventory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.booking.hotelinventory.dto.HotelPatchRequest;
import org.booking.hotelinventory.dto.HotelRequest;
import org.booking.hotelinventory.dto.HotelResponse;
import org.booking.hotelinventory.exception.custom.HotelNotFoundException;
import org.booking.hotelinventory.mapper.HotelMapper;
import org.booking.hotelinventory.model.Hotel;
import org.booking.hotelinventory.repository.HotelRepository;
import org.booking.hotelinventory.service.HotelService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    @Override
    public HotelResponse createHotel(HotelRequest request) {
        Hotel hotel = hotelMapper.toHotel(request);
        hotelRepository.save(hotel);
        log.info("Hotel with id {} has been created", hotel.getId());
        return hotelMapper.toResponse(hotel);
    }

    @Override
    public HotelResponse getHotelById(String id) {
        return hotelMapper.toResponse(getExistingHotel(id));
    }

    @Override
    public HotelResponse updateHotel(String id, HotelPatchRequest request) {
        Hotel hotel = getExistingHotel(id);
        applyUpdate(hotel, request);

        hotelRepository.save(hotel);

        log.info("Hotel updated with ID: {}", id);

        return hotelMapper.toResponse(hotel);
    }

    @Override
    public HotelResponse searchHotels(String name) {
        return hotelMapper.toResponse(hotelRepository.findByName(name));
    }

    @Override
    public List<HotelResponse> allHotels() {
        return hotelRepository.findAll().stream()
                .map(hotelMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteHotelById(String id) {

        getExistingHotel(id);

        log.info("Hotel with id {} has been deleted", id);

        hotelRepository.deleteById(id);
    }

    private Hotel getExistingHotel(String id) {
        return hotelRepository.findById(id).orElseThrow(() -> new HotelNotFoundException(id));
    }

    private void applyUpdate(Hotel hotel, HotelPatchRequest request) {
        Optional.ofNullable(hotel.getName()).ifPresent(hotel::changeName);
        Optional.ofNullable(request.city()).ifPresent(hotel::changeCity);
        Optional.ofNullable(request.availableRooms()).ifPresent(hotel::changeAvailableRooms);
        Optional.ofNullable(request.pricePerNight()).ifPresent(hotel::changePricePerNight);
    }
}
