package com.aportme.backend.component.security.service;

import com.aportme.backend.component.user.entity.User;
import com.aportme.backend.component.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userFromDB = userRepository.findByEmail(email);

        if(userFromDB.isEmpty()){
            throw new UsernameNotFoundException(email);
        }

        return userFromDB.get();
    }
}
