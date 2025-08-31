package com.markokosic.minicrm.service;

import com.markokosic.minicrm.dto.response.AuthResponse;
import com.markokosic.minicrm.dto.response.UserResponse;
import com.markokosic.minicrm.dto.request.LoginRequest;
import com.markokosic.minicrm.model.User;
import com.markokosic.minicrm.mapper.UserMapper;
import com.markokosic.minicrm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Autowired
    JWTService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

//    public UserResponse register(UserResponse userResponse) {
//        return null;
//    }

    public UserResponse getUserById(Long id) {
        return null;
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public void deleteUser(Long id) {

    }

    public UserResponse convertToResponseDto(User user) {
        return userMapper.userToUserResponse(user);
    }

    public AuthResponse verify(LoginRequest user) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

            if(authentication.isAuthenticated()){
                User userData = userRepository.findByEmail(user.getEmail());
                System.out.println(userData);
                String accessToken = jwtService.generateToken(user.getEmail());
                System.out.println(accessToken);
                UserResponse userResponse = new UserResponse(userData.getFirstName(), userData.getLastName(), userData.getEmail());
				return new AuthResponse(accessToken, userResponse);
            }
            else {
               return new AuthResponse("ABCABC", null);
//              throw new BadCredentialsException("Bad credentials");
            }
    }
}
