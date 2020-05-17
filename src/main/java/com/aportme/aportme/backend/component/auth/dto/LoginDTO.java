package com.aportme.aportme.backend.component.auth.dto;

import com.aportme.aportme.backend.utils.dto.DTOEntity;
import lombok.Data;

@Data
public class LoginDTO implements DTOEntity {

    private String username;

    private String password;

    private String clientId;

    private String grantType;
}
