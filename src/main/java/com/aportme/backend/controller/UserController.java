package com.aportme.backend.controller;

import com.aportme.backend.entity.dto.user.AuthUserDTO;
import com.aportme.backend.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @ApiOperation(value = "Register new user")
    public void registerUser(@RequestBody AuthUserDTO user) {
        userService.registerUser(user);
    }
}
