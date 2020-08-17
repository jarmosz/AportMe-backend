package com.aportme.backend.entity.dto.user;

import com.aportme.backend.entity.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO extends UserBaseDTO {

    private Long id;

    private Role role;
}
