package com.aportme.aportme.backend.security.controller;

import com.aportme.aportme.backend.component.user.entity.User;
import com.aportme.aportme.backend.component.user.enums.Role;
import com.aportme.aportme.backend.component.user.repository.UserRepository;
import com.aportme.aportme.backend.security.exception.UserAlreadyExists;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RegisterController {

    @Autowired
    private final UserRepository userRepository;

    @PostMapping("/register")
    public void register(@RequestBody User user){
        User foundedUser = userRepository.findByEmail(user.getEmail());

        if(foundedUser != null){
            throw new UserAlreadyExists("User with this email already exists");
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
    }
}
