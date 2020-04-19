package com.aportme.aportme.backend.component.userInfo.controller;

import com.aportme.aportme.backend.component.userInfo.service.UserInfoService;
import com.aportme.aportme.backend.utils.dto.DTOEntity;
import com.aportme.aportme.backend.component.userInfo.dto.AddUserInfoDTO;
import com.aportme.aportme.backend.component.userInfo.dto.UpdateUserInfoDTO;
import com.aportme.aportme.backend.component.userInfo.dto.UserInfoDTO;
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
    public DTOEntity update(@PathVariable Long id, @RequestBody UpdateUserInfoDTO userDTO) throws Exception {
        return userInfoService.update(id, userDTO);
    }

    @ApiOperation(value = "Creat user details", response = UserInfoDTO.class)
    public DTOEntity create(@RequestParam Long userId, @RequestBody AddUserInfoDTO userDTO) throws Exception {
        return userInfoService.create(userId, userDTO);
    }
}
