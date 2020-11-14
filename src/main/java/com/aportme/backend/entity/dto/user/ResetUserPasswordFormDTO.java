package com.aportme.backend.entity.dto.user;

import lombok.Data;

@Data
public class ResetUserPasswordFormDTO {
    private String newPassword;
    private String repeatedNewPassword;
    private String resetPasswordLinkToken;
}
