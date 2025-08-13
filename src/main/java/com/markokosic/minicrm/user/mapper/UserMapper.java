package com.markokosic.minicrm.user.mapper;

import com.markokosic.minicrm.user.dto.UserDto;
import com.markokosic.minicrm.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel =  "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto userToUserDto(User user);
}
