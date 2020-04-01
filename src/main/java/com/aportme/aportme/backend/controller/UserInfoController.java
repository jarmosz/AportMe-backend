package com.aportme.aportme.backend.controller;

import com.aportme.aportme.backend.dto.DTOEntity;
import com.aportme.aportme.backend.dto.user.userInfo.AddUserDTO;
import com.aportme.aportme.backend.dto.user.userInfo.UpdateUserDTO;
import com.aportme.aportme.backend.entity.user.UserInfo;
import com.aportme.aportme.backend.service.UserInfoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/users/info")
public class UserInfoController {
    private final UserInfoService userInfoService;

    @GetMapping("/{id}")
    public DTOEntity getById(@PathVariable Long id) {
        return userInfoService.getById(id);
    }

    @PutMapping("/{id}")
    public DTOEntity update(@PathVariable Long id, @RequestBody UpdateUserDTO userDTO) throws Exception {
        return userInfoService.update(id, userDTO);
    }

    @PostMapping
    public UserInfo create(@RequestParam Long userId, @RequestBody AddUserDTO userDTO) throws Exception {
        return userInfoService.create(userId, userDTO);
    }
}
