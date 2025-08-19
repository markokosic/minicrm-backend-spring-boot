package com.markokosic.minicrm.service;

import com.markokosic.minicrm.dto.UserDto;
import com.markokosic.minicrm.model.User;
import com.markokosic.minicrm.mapper.UserMapper;
import com.markokosic.minicrm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserDto register(UserDto userDto) {
        return null;
    }

    public UserDto getUserById(Long id) {
        return null;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    public void deleteUser(Long id) {

    }

    public UserDto convert(User user) {
        return userMapper.userToUserDto(user);
    }
}
