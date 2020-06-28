package com.aportme.backend.component.activationToken.controller;

import com.aportme.backend.component.activationToken.service.ActivationTokenService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/account/activation")
public class ActivationTokenController {

    private final ActivationTokenService activationTokenService;

    @GetMapping
    @ApiOperation("Activating user account by passing token in params.")
    public ResponseEntity<Object> activateAccount(@RequestParam("token") String token) {
        return activationTokenService.confirmActivationToken(token);
    }

    @GetMapping("/resend")
    @ApiOperation("Resend email activation token")
    public ResponseEntity<Object> resendActivationMail(@RequestParam("email") String email) {
        return activationTokenService.resendActivationToken(email);
    }

}
