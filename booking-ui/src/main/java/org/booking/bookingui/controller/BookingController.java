package org.booking.bookingui.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.booking.bookingui.dto.*;
import org.booking.bookingui.service.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/index")
    public String index(Model model) {
        loadReferenceData(model);
        return "index";
    }

    @GetMapping("/booking")
    public String showBooking(@RequestParam(required = false) String flightId, @RequestParam(required = false) String hotelId, Model model) {
        loadReferenceData(model);
        model.addAttribute("selectedFlightId", flightId);
        model.addAttribute("selectedHotelId", hotelId);
        return "booking";
    }

    @PostMapping("/orders")
    @ResponseBody
    public OrderResponse createOrder(
            @RequestBody OrderRequest request) {
        return bookingService.createOrder(request);
    }

    @GetMapping("/my-orders")
    public String orders(Model model, HttpSession session) {

        List<OrderResponse> orders = bookingService.getUserOrders();

        model.addAttribute("orders", orders);
        model.addAttribute("email", session.getAttribute("email"));

        return "orders";
    }

    @GetMapping("/composition/{id}")
    @ResponseBody
    public BookingDetailsDto getBookingDetails(@PathVariable String id) {
        return bookingService.getBookingDetails(id);
    }

    @GetMapping("/orders/{id}")
    @ResponseBody
    public OrderResponse getOrderStatus(@PathVariable String id) {
        return bookingService.getOrderStatus(id);
    }

    private void loadReferenceData(Model model) {
        model.addAttribute("flights", bookingService.getFlights());
        model.addAttribute("hotels", bookingService.getHotels());
    }
}