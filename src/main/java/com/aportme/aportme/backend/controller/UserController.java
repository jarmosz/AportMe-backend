package com.aportme.aportme.backend.controller;

import com.aportme.aportme.backend.dto.DTOEntity;
import com.aportme.aportme.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public DTOEntity getById(@PathVariable Long id) {
        return  userService.getUserById(id);
    }
}
