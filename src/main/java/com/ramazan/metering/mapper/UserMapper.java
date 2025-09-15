package com.ramazan.metering.mapper;

import com.ramazan.metering.controller.dto.UserResponseDto;
import com.ramazan.metering.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto toDto(User user);
}
