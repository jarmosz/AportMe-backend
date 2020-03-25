package com.aportme.aportme.backend.controller;

import com.aportme.aportme.backend.entity.ApplicationUser;
import com.aportme.aportme.backend.repository.ApplicationUserRepository;
import com.aportme.aportme.backend.security.Role;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class ApplicationUserController {

    private ApplicationUserRepository applicationUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/sign-up")
    public void signUp(@RequestBody ApplicationUser applicationUser){
        applicationUser.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
        applicationUser.setRole(Role.USER);
        applicationUser.setEnabled(true);
        applicationUserRepository.save(applicationUser);
    }
}
