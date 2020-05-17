package com.aportme.aportme.backend.component.auth.controller;

import com.aportme.aportme.backend.component.auth.dto.AuthorizedDTO;
import com.aportme.aportme.backend.component.auth.dto.LoginDTO;
import com.aportme.aportme.backend.component.auth.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthorizedDTO login(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }
}
