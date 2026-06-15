package org.booking.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.booking.authservice.dto.AuthResponse;
import org.booking.authservice.dto.LoginRequest;
import org.booking.authservice.dto.RegisterRequest;
import org.booking.authservice.exception.custom.InvalidCredentialsException;
import org.booking.authservice.exception.custom.UserAlreadyExistsException;
import org.booking.authservice.mapper.AuthMapper;
import org.booking.authservice.model.User;
import org.booking.authservice.model.enums.Role;
import org.booking.authservice.repository.UserRepository;
import org.booking.authservice.service.AuthService;
import org.booking.authservice.service.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthMapper authMapper;

    @Override
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        log.info("User logged successfully : {}", user.getId());

        String token = jwtService.generateToken(user);

        return authMapper.toResponse(user,token);
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException(request.email());
        }

        User user = authMapper.toUser(request);

        user.changePassword(passwordEncoder.encode(request.password()));

        userRepository.save(user);

        log.info("User registered successfully : {}", user.getId());

        String token = jwtService.generateToken(user);

        return authMapper.toResponse(user,token);
    }

    public AuthResponse updateRole(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(InvalidCredentialsException::new);

        user.changeRole(Role.ADMIN);

        userRepository.save(user);

        return  authMapper.toResponse(user,null);
    }


}
