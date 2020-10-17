package com.aportme.backend.controller;

import com.aportme.backend.entity.dto.TokenPairDTO;
import com.aportme.backend.entity.dto.UserLoginDTO;
import com.aportme.backend.service.security.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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

    private final SecurityService securityService;

    @PostMapping("/login")
    public TokenPairDTO login(@RequestBody UserLoginDTO userLoginDTO){
        return securityService.loginUser(userLoginDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request){
        securityService.logoutUser(request);
        return new ResponseEntity<>("User logged out.", HttpStatus.NO_CONTENT);
    }

    @PostMapping("/refreshToken")
    public TokenPairDTO refreshToken(HttpServletRequest request){
        return securityService.refreshAccessToken(request);
    }
}
