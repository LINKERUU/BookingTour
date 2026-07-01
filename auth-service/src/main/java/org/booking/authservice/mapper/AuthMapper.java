package org.booking.authservice.mapper;

import org.booking.authservice.dto.AuthResponse;
import org.booking.authservice.dto.RegisterRequest;
import org.booking.authservice.dto.UserResponse;
import org.booking.authservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    @Mapping(target = "userId", source = "user.id")
    AuthResponse toResponse(User user, String token);
    User toUser(RegisterRequest user);

    @Mapping(source = "user.id", target = "userId")
    UserResponse toUserResponse(User user);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "token", ignore = true)
    AuthResponse toResponse(User user);

}
