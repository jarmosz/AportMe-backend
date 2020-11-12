package com.aportme.backend.controller;

import com.aportme.backend.entity.dto.user.AuthUserDTO;
import com.aportme.backend.entity.dto.user.ChangeUserPasswordDTO;
import com.aportme.backend.entity.dto.user.ResetUserPasswordDTO;
import com.aportme.backend.service.ResetPasswordTokenService;
import com.aportme.backend.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final ResetPasswordTokenService resetPasswordTokenService;

    @PostMapping("/register")
    @ApiOperation(value = "Register new user")
    public void registerUser(@RequestBody AuthUserDTO user) {
        userService.registerUser(user);
    }

    @PostMapping("/changePassword")
    @ApiOperation(value = "Change user password")
    public ResponseEntity changeUserPassword(@RequestBody ChangeUserPasswordDTO passwords) {
        userService.changeUserPassword(passwords);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sendResetPasswordLink")
    @ApiOperation(value = "Send email with link to reset password")
    public ResponseEntity sendResetPasswordLink(@RequestBody ResetUserPasswordDTO resetUserPasswordDTO) {
        userService.sendResetPasswordLink(resetUserPasswordDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/resetPassword")
    @ApiOperation("Reset user password")
    public ResponseEntity<Object> resetUserPasswordFromForm(@RequestParam("linkToken") String linkToken, ChangeUserPasswordDTO changeUserPasswordDTO) {
        resetPasswordTokenService.checkResetPasswordToken(linkToken);
        userService.changeUserPassword(changeUserPasswordDTO);
        return ResponseEntity.ok().build();
    }
}
