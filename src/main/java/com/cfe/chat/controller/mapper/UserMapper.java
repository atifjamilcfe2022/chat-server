package com.cfe.chat.controller.mapper;

import com.cfe.chat.controller.dto.UserDto;
import com.cfe.chat.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

//    @Mapping(source = "source.name", target = "target.name")
    UserDto toUserDto(User user);

    User toUser(UserDto userDto);
}
