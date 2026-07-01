package org.booking.authservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.booking.authservice.dto.UserRequest;
import org.booking.authservice.dto.UserResponse;
import org.booking.authservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getMe(@RequestHeader("X-User-Id") String userId) {
        return userService.getMe(userId);
    }

    @PostMapping("/me/deposit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserResponse deposit(@RequestHeader("X-User-Id") String userId,
                                @Valid @RequestBody UserRequest request) {
        return userService.deposit(userId, request);
    }

}
