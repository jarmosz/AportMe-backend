package com.aportme.aportme.backend.dto.user;

import com.aportme.aportme.backend.dto.DTOEntity;
import com.aportme.aportme.backend.entity.user.Role;
import lombok.Data;

@Data
public class UserDTO implements DTOEntity {

    private Long id;

    private String email;

    private Role role;
}
