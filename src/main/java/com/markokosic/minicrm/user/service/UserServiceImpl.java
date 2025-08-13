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
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto register(UserDto userDto) {
        return null;
    }

    @Override
    public UserDto getUserById(Long id) {
        return null;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserMapper.INSTANCE::userToUserDto)                         .collect(Collectors.toList());

    }

    @Override
    public void deleteUser(Long id) {

    }
}
