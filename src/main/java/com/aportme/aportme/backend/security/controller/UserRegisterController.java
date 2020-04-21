package com.aportme.aportme.backend.security.controller;

import com.aportme.aportme.backend.component.user.entity.User;
import com.aportme.aportme.backend.security.service.UserRegisterService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserRegisterController {

    private final UserRegisterService userRegisterService;

    @PostMapping("/register")
    public void register(@RequestBody User user) throws Exception {
        userRegisterService.registerUser(user);
    }
}
