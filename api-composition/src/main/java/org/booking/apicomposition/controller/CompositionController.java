package org.booking.apicomposition.controller;

import lombok.RequiredArgsConstructor;
import org.booking.apicomposition.dto.BookingDetailsResponse;
import org.booking.apicomposition.service.CompositionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class CompositionController {

    private final String ORDER_ID = "/{orderId}";
    private final CompositionService compositionService;

    @GetMapping(ORDER_ID)
    public BookingDetailsResponse getBookingDetails(@PathVariable String orderId) {
        return compositionService.getBookingDetails(orderId);
    }
}
