package com.aportme.backend.entity.dto;

import lombok.Data;

@Data
public class UserLoginDTO {

    private String email;

    private String password;
}
