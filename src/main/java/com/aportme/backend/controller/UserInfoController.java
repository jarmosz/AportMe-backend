package com.aportme.backend.controller;

import com.aportme.backend.entity.dto.userInfo.AddUserInfoDTO;
import com.aportme.backend.entity.dto.userInfo.UpdateUserInfoDTO;
import com.aportme.backend.entity.dto.userInfo.UserInfoDTO;
import com.aportme.backend.service.UserInfoService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/users/info")
public class UserInfoController {

    private final UserInfoService userInfoService;

    @GetMapping("/{id}")
    @ApiOperation(value = "Find details about user by id", response = UserInfoDTO.class)
    public UserInfoDTO getById(@PathVariable Long id) {
        return userInfoService.getById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update user details")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody UpdateUserInfoDTO userDTO) {
        return userInfoService.update(id, userDTO);
    }

    @PostMapping
    @ApiOperation(value = "Creat user details", response = UserInfoDTO.class)
    public ResponseEntity<Object> create(@RequestParam Long userId, @RequestBody AddUserInfoDTO userDTO) {
        return userInfoService.create(userId, userDTO);
    }
}
