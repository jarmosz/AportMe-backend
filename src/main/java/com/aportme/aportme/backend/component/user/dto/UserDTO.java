package com.aportme.aportme.backend.component.user.dto;

import com.aportme.aportme.backend.component.user.enums.Role;
import com.aportme.aportme.backend.utils.dto.DTOEntity;
import lombok.Data;

@Data
public class UserDTO implements DTOEntity {

    private Long id;

    private String email;

    private Role role;
}
