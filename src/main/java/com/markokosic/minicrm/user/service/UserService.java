package com.markokosic.minicrm.user.service;

import com.markokosic.minicrm.user.dto.UserDto;
import com.markokosic.minicrm.user.mapper.UserMapper;
import com.markokosic.minicrm.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto register(UserDto userDto) {
        return null;
    }

    public UserDto getUserById(Long id) {
        return null;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserMapper.INSTANCE::userToUserDto)                         .collect(Collectors.toList());

    }

    public void deleteUser(Long id) {

    }
}
