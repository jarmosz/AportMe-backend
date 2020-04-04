package com.aportme.aportme.backend.component.user.controller;

import com.aportme.aportme.backend.component.user.dto.UserDTO;
import com.aportme.aportme.backend.component.user.service.UserService;
import com.aportme.aportme.backend.utils.dto.DTOEntity;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
}
