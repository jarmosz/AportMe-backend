package com.aportme.backend.component.security.controller;

import com.aportme.backend.component.security.dto.AccessTokenDTO;
import com.aportme.backend.component.security.dto.TokenPairDTO;
import com.aportme.backend.component.security.dto.UserLoginDTO;
import com.aportme.backend.component.security.service.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class SecurityController {

    private SecurityService securityService;

    @PostMapping("/login")
    public TokenPairDTO loginUser(@RequestBody UserLoginDTO userLoginDTO){
        return securityService.loginUser(userLoginDTO);
    }

    @PostMapping("/logout")
    public void logoutUser(ServletRequest request, ServletResponse response){
        securityService.logoutUser(request, response);
    }

    @PostMapping("/refreshToken")
    public AccessTokenDTO refreshToken(ServletRequest request, ServletResponse response){
        return securityService.refreshAccessToken(request, response);
    }
}
