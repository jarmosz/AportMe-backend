package com.aportme.backend.controller;

import com.aportme.backend.entity.dto.user.ResetUserPasswordDTO;
import com.aportme.backend.entity.dto.user.ResetUserPasswordFormDTO;
import com.aportme.backend.service.ResetPasswordService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reset-password")
public class ResetPasswordController {

    private final ResetPasswordService resetPasswordService;

    @PostMapping("/generate-link")
    @ApiOperation(value = "Send email with link to reset password")
    public ResponseEntity<Object> sendResetPasswordLink(@RequestBody ResetUserPasswordDTO resetUserPasswordDTO) {
        resetPasswordService.sendResetPasswordEmail(resetUserPasswordDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/validate-token")
    @ApiOperation("Validate token from reset password link")
    public ResponseEntity<Object> checkResetPasswordToken(@RequestParam("token") String linkToken) {
        resetPasswordService.checkResetPasswordToken(linkToken);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @ApiOperation("Reset user password")
    public ResponseEntity<Object> changeUserPassword(@RequestBody ResetUserPasswordFormDTO resetUserPasswordFormDTO) {
        resetPasswordService.changeUserPassword(resetUserPasswordFormDTO);
        return ResponseEntity.ok().build();
    }
}
