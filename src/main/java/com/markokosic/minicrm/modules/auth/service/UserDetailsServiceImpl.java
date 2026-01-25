package com.markokosic.minicrm.modules.auth.service;

import com.markokosic.minicrm.modules.user.User;
import com.markokosic.minicrm.modules.auth.model.UserPrincipal;
import com.markokosic.minicrm.modules.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {

        Optional<User> optionalUser = userRepository.findByEmail(mail);

        if(optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        User user = optionalUser.get();

        return new UserPrincipal(user);
    }
}
