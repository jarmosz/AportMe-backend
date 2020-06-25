package com.aportme.backend.component.activationToken.controller;

import com.aportme.backend.component.activationToken.service.ActivationTokenService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ActivationTokenController {

    private ActivationTokenService activationTokenService;

    @GetMapping("/activateAccount")
    @ApiOperation("Activating user account by passing token in params.")
    public ResponseEntity<Object> activateAccount(@RequestParam("token") String token) {
        return activationTokenService.confirmActivationToken(token);
    }
}
