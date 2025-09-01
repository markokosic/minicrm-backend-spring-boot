package com.markokosic.minicrm.service;

import com.markokosic.minicrm.dto.response.UserResponse;
import com.markokosic.minicrm.mapper.UserMapper;
import com.markokosic.minicrm.model.User;
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


    public UserResponse getUserById(Long id) {
        return null;
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToUserResponseDto)
                .collect(Collectors.toList());
    }

    public void deleteUser(Long id) {

    }

    public UserResponse convertToUserResponseDto(User user) {
        return userMapper.userToUserResponse(user);
    }


}
