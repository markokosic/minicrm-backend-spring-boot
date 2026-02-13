package com.markokosic.minicrm.modules.user;

import com.markokosic.minicrm.modules.user.dto.response.UserResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO userToUserResponseDTO(User user);
}
