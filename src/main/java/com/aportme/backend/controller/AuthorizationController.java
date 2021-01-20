package com.aportme.backend.controller;

import com.aportme.backend.entity.dto.TokenPairDTO;
import com.aportme.backend.entity.dto.UserLoginDTO;
import com.aportme.backend.entity.dto.user.AuthUserDTO;
import com.aportme.backend.entity.dto.user.ChangeUserPasswordDTO;
import com.aportme.backend.entity.enums.RequestSource;
import com.aportme.backend.facade.AuthorizationFacade;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authorization")
public class AuthorizationController {

    private final AuthorizationFacade facade;

    @PostMapping("/login")
    public TokenPairDTO login(
            @RequestParam(required = false) RequestSource requestSource,
            @RequestBody UserLoginDTO userLoginDTO) {
        return facade.loginUser(userLoginDTO, requestSource);
    }

    @PostMapping("/refresh-token")
    public TokenPairDTO refreshToken(HttpServletRequest request) {
        return facade.refreshAccessToken(request);
    }

    @PostMapping("/register")
    @ApiOperation(value = "Register new user")
    public TokenPairDTO registerUser(@RequestBody AuthUserDTO user) {
        return facade.registerUser(user);
    }

    @PostMapping("/change-password")
    @ApiOperation(value = "Change user password")
    public ResponseEntity<Object> changeUserPassword(@RequestBody ChangeUserPasswordDTO passwords) {
        facade.changeUserPassword(passwords);
        return ResponseEntity.ok().build();
    }
}
