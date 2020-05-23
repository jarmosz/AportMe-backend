package com.aportme.aportme.backend.component.auth.controller;

import com.aportme.aportme.backend.component.auth.dto.AuthorizedDTO;
import com.aportme.aportme.backend.component.auth.dto.GetTokenDTO;
import com.aportme.aportme.backend.component.auth.TokenType;
import com.aportme.aportme.backend.component.auth.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthorizedDTO> login(@RequestBody GetTokenDTO getTokenDTO) {
        return authService.getToken(getTokenDTO, TokenType.ACCESS);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthorizedDTO> refresh(@RequestBody GetTokenDTO refreshTokenDTO) {
        return authService.getToken(refreshTokenDTO, TokenType.REFRESH);
    }
}
