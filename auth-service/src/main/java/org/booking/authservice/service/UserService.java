package org.booking.authservice.service;

import org.booking.authservice.dto.UserRequest;
import org.booking.authservice.dto.UserResponse;
import org.booking.sharedlib.messaging.event.BookingCommand;

import java.math.BigDecimal;

public interface UserService {

    void withdraw(String userId, BigDecimal amount);

    void refund(BookingCommand command);

    UserResponse getMe(String userId);

    UserResponse deposit(String userId, UserRequest request);
}
