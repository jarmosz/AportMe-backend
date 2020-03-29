package com.aportme.aportme.backend.controller;

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
    public UserInfo getById(@PathVariable Long id) {
        return userInfoService.getById(id);
    }

    @PutMapping("/{id}")
    public UserInfo update(@PathVariable Long id, @RequestBody UserInfo userInfo) {
        return userInfoService.update(id, userInfo);
    }

    @PostMapping
    public UserInfo create(@RequestParam Long userId, @RequestBody UserInfo userInfo) {
        return userInfoService.create(userId, userInfo);
    }
}
