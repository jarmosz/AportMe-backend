package com.aportme.backend.component.security.controller;

import com.aportme.backend.component.security.dto.TokenPairDTO;
import com.aportme.backend.component.security.dto.UserLoginDTO;
import com.aportme.backend.component.security.service.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class SecurityController {

    private SecurityService securityService;

    @PostMapping("/login")
    public TokenPairDTO login(@RequestBody UserLoginDTO userLoginDTO){
        return securityService.loginUser(userLoginDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request){
        return securityService.logoutUser(request);
    }

    @PostMapping("/refreshToken")
    public TokenPairDTO refreshToken(HttpServletRequest request){
        return securityService.refreshAccessToken(request);
    }
}
