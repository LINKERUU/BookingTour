package org.booking.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.booking.authservice.dto.UserRequest;
import org.booking.authservice.dto.UserResponse;
import org.booking.authservice.exception.custom.InvalidAmount;
import org.booking.authservice.exception.custom.UserNotFoundException;
import org.booking.authservice.mapper.AuthMapper;
import org.booking.authservice.model.User;
import org.booking.authservice.repository.UserRepository;
import org.booking.authservice.service.UserService;
import org.booking.sharedlib.messaging.event.BookingCommand;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthMapper userMapper;

    @Override
    public void withdraw(String userId, BigDecimal amount) {

        checkAmount(amount, "списания");
        User user = getExistUser(userId);

        if (user.getBalance().compareTo(amount) < 0)
            throw new InvalidAmount("Недостаточно средств на балансе");

        user.withdraw(amount);
        userRepository.save(user);

        log.info("Успешно списано {} у пользователя {}", amount, userId);
    }


    @Override
    public void refund(BookingCommand command) {
        User user = getExistUser(command.userId());

        user.deposit(command.amount());
        userRepository.save(user);

        log.info("Сделан возврат средств {} для пользователя {}, заказ {}",
                command.amount(), command.userId(), command.orderId());
    }

    @Override
    public UserResponse getMe(String userId) {
        return userMapper.toUserResponse(getExistUser(userId));
    }


    @Override
    public UserResponse deposit(String userId, UserRequest request) {

        checkAmount(request.amount(), "пополнения");
        User user = getExistUser(userId);
        user.deposit(request.amount());
        userRepository.save(user);

        return userMapper.toUserResponse(getExistUser(userId));
    }

    private User getExistUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    private void checkAmount(BigDecimal amount, String typeOperation) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmount("Некорректная сумма для списания для " + typeOperation);
        }
    }
}