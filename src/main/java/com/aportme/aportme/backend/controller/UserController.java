package com.aportme.aportme.backend.controller;

import com.aportme.aportme.backend.entity.User;
import com.aportme.aportme.backend.repository.UserRepository;
import com.aportme.aportme.backend.security.Role;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController("/users")
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public void register(@RequestBody User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user.setEnabled(true);
        userRepository.save(user);
    }

    @GetMapping("/login")
    public void login(@RequestParam("username") String username, @RequestParam("password") String password) {

    }
}
