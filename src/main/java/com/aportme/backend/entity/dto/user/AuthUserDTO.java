package com.aportme.backend.entity.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthUserDTO extends UserBaseDTO {

    private String password;
}
