package com.aportme.aportme.backend.controller;

import com.aportme.aportme.backend.dto.DTOEntity;
import com.aportme.aportme.backend.dto.user.userInfo.AddUserDTO;
import com.aportme.aportme.backend.dto.user.userInfo.UpdateUserDTO;
import com.aportme.aportme.backend.dto.user.userInfo.UserInfoDTO;
import com.aportme.aportme.backend.service.UserInfoService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/users/info")
public class UserInfoController {

    private final UserInfoService userInfoService;

    @ApiOperation(value = "Find details about user by id", response = UserInfoDTO.class)
    @GetMapping("/{id}")
    public DTOEntity getById(@PathVariable Long id) {
        return userInfoService.getById(id);
    }

    @ApiOperation(value = "Update user details", response = UserInfoDTO.class)
    @PutMapping("/{id}")
    public DTOEntity update(@PathVariable Long id, @RequestBody UpdateUserDTO userDTO) throws Exception {
        return userInfoService.update(id, userDTO);
    }

    @ApiOperation(value = "Creat user details", response = UserInfoDTO.class)
    @PostMapping
    public DTOEntity create(@RequestParam Long userId, @RequestBody AddUserDTO userDTO) throws Exception {
        return userInfoService.create(userId, userDTO);
    }
}
