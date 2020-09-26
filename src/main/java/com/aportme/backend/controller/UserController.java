package com.aportme.backend.controller;

import com.aportme.backend.entity.dto.user.AuthUserDTO;
import com.aportme.backend.entity.dto.user.UserDTO;
import com.aportme.backend.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    @ApiOperation(value = "Find user by id", response = UserDTO.class)
    public UserDTO getUSerById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PostMapping("/register")
    @ApiOperation(value = "Register new user")
    public void registerUser(@RequestBody AuthUserDTO user) {
        userService.registerUser(user);
    }
}
