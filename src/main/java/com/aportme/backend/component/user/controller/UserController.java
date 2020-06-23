package com.aportme.backend.component.user.controller;

import com.aportme.backend.component.user.dto.UserDTO;
import com.aportme.backend.component.user.entity.User;
import com.aportme.backend.component.user.service.UserService;
import com.aportme.backend.utils.dto.DTOEntity;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "Find user by id", response = UserDTO.class)
    @GetMapping("/{id}")
    public DTOEntity getById(@PathVariable Long id) {
        return  userService.getUserById(id);
    }

    @ApiOperation(value = "Register new user")
    @PostMapping("/register")
    public void registerUser(@RequestBody User user, HttpServletRequest request) {
        userService.registerUser(user, request);
    }
}
