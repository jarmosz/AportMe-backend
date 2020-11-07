package com.aportme.backend.entity.dto.user;

import lombok.Data;

@Data
public class ChangeUserPasswordDTO {

    private String oldPassword;

    private String newPassword;

    private String repeatedNewPassword;
}
