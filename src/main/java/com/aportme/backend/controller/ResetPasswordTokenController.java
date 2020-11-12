package com.aportme.backend.controller;

import com.aportme.backend.entity.dto.user.ResetUserPasswordFormDTO;
import com.aportme.backend.service.ResetPasswordTokenService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users/resetPassword")
public class ResetPasswordTokenController {

    private final ResetPasswordTokenService resetPasswordTokenService;

    @GetMapping("/validateToken")
    @ApiOperation("Validate token from reset password link")
    public ResponseEntity<Object> checkResetPasswordToken(@RequestParam("linkToken") String linkToken) {
        resetPasswordTokenService.checkResetPasswordToken(linkToken);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/changePassword")
    @ApiOperation("Change user password from reset password form")
    public ResponseEntity<Object> changeUserPassword(@RequestBody ResetUserPasswordFormDTO resetUserPasswordFormDTO) {
        resetPasswordTokenService.changeUserPassword(resetUserPasswordFormDTO);
        return ResponseEntity.ok().build();
    }

}
