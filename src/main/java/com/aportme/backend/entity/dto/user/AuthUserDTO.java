package com.aportme.backend.entity.dto.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthUserDTO extends UserBaseDTO {

    private String password;
}
