package com.aportme.backend.controller;

import com.aportme.backend.entity.dto.TokenPairDTO;
import com.aportme.backend.entity.dto.UserLoginDTO;
import com.aportme.backend.service.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SecurityController {

    private final SecurityService securityService;

    @PostMapping("/login")
    public TokenPairDTO login(@RequestBody UserLoginDTO userLoginDTO) {
        return securityService.loginUser(userLoginDTO);
    }

    @PostMapping("/refreshToken")
    public TokenPairDTO refreshToken(HttpServletRequest request) {
        return securityService.refreshAccessToken(request);
    }
}
