package org.booking.bookingui.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.booking.bookingui.dto.LoginRequest;
import org.booking.bookingui.dto.RegisterRequest;
import org.booking.bookingui.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/")
    public String home(HttpSession session) {
        return authService.checkToken( (String) session.getAttribute("jwt"));
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest request, HttpSession session) {
        session.setAttribute("jwt", Objects.requireNonNull(authService.login(request)).token());
        return "redirect:/index";

    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterRequest request) {

        authService.register(request);

        return "redirect:/login";
    }
}
