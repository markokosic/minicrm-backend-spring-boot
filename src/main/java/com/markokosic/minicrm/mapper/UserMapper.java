package com.markokosic.minicrm.mapper;

import com.markokosic.minicrm.dto.response.UserResponse;
import com.markokosic.minicrm.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse userToUserResponse(User user);
}
