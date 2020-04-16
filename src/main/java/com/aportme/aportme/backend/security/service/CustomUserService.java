package com.aportme.aportme.backend.security.service;

import com.aportme.aportme.backend.component.user.entity.User;
import com.aportme.aportme.backend.component.user.repository.UserRepository;
import com.aportme.aportme.backend.security.entity.CustomUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(email);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUser(user);
    }
}