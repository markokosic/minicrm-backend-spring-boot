package com.markokosic.minicrm.user.service;

import com.markokosic.minicrm.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto register(UserDto userDto);
    UserDto getUserById(Long id);
    List<UserDto> getAllUsers();
    void deleteUser(Long id);
}
