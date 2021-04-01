package com.management.student.app.mapper;

import com.management.student.app.dto.UserDto;
import com.management.student.app.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper (UserMapper.class);

    @Mapping(target = "password",ignore = true)
    User toUser(UserDto userDto);
}
