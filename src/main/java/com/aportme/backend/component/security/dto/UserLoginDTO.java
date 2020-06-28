package com.aportme.backend.component.security.dto;

import com.aportme.backend.utils.dto.DTOEntity;
import lombok.Data;

@Data
public class UserLoginDTO implements DTOEntity {

    private String email;

    private String password;
}
