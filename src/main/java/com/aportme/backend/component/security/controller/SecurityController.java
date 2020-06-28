package com.aportme.backend.component.security.controller;

import com.aportme.backend.component.security.dto.TokenPairDTO;
import com.aportme.backend.component.security.dto.UserLoginDTO;
import com.aportme.backend.component.security.service.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class SecurityController {

    private SecurityService securityService;

    @PostMapping("/login")
    public TokenPairDTO loginUser(@RequestBody UserLoginDTO userLoginDTO){
        return securityService.loginUser(userLoginDTO);
    }
}
