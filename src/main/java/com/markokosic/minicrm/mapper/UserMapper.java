package com.markokosic.minicrm.mapper;

import com.markokosic.minicrm.dto.UserDto;
import com.markokosic.minicrm.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel =  "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto userToUserDto(User user);
}
