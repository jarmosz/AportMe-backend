package com.aportme.aportme.backend.dto.user.userInfo;

import com.aportme.aportme.backend.dto.DTOEntity;
import lombok.Data;

@Data
public class UpdateUserDTO implements DTOEntity {

    private String name;

    private String surname;

    private String phoneNumber;
}
