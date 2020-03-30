package com.aportme.aportme.backend.controller;

import com.aportme.aportme.backend.dto.DTOEntity;
import com.aportme.aportme.backend.dto.UserInfoDTO;
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
    public DTOEntity update(@PathVariable Long id, @RequestBody UserInfoDTO userInfoDTO) throws Exception {
        return userInfoService.update(id, userInfoDTO);
    }

    @PostMapping
    public DTOEntity create(@RequestParam Long userId, @RequestBody UserInfoDTO userInfoDTO) throws Exception {
        return userInfoService.create(userId, userInfoDTO);
    }
}
