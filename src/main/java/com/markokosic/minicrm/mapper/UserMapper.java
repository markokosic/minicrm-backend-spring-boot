package com.markokosic.minicrm.mapper;

import com.markokosic.minicrm.dto.UserDto;
import com.markokosic.minicrm.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userToUserDto(User user);
}
