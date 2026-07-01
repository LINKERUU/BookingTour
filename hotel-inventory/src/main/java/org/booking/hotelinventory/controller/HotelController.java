package org.booking.hotelinventory.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.booking.hotelinventory.dto.HotelPatchRequest;
import org.booking.hotelinventory.dto.HotelRequest;
import org.booking.hotelinventory.dto.HotelResponse;
import org.booking.hotelinventory.service.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;
    private final String ID = "/{id}";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HotelResponse createHotel(@Valid @RequestBody HotelRequest request) {
        return hotelService.createHotel(request);
    }

    @GetMapping(ID)
    @ResponseStatus(HttpStatus.OK)
    public HotelResponse getHotelById(@PathVariable String id) {
        return hotelService.getHotelById(id);
    }

    @PatchMapping(ID)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public HotelResponse updateHotelById(@PathVariable String id, @Valid @RequestBody HotelPatchRequest request) {
        return hotelService.updateHotel(id, request);
    }

    @DeleteMapping(ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHotelById(@PathVariable String id) {
        hotelService.deleteHotelById(id);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public HotelResponse searchHotelByName(@RequestParam String name) {
        return hotelService.searchHotels(name);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<HotelResponse> searchHotels() {
        return hotelService.allHotels();
    }
}
