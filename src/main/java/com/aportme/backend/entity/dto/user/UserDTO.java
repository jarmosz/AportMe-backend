package com.aportme.backend.entity.dto.user;

import com.aportme.backend.entity.enums.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends UserBaseDTO {

    private Long id;

    private Role role;
}
