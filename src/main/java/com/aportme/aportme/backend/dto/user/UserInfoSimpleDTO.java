package com.aportme.aportme.backend.dto.user;

import com.aportme.aportme.backend.dto.DTOEntity;
import lombok.Data;

@Data
public class UserInfoSimpleDTO implements DTOEntity {

    private Long id;

    private String name;

    private String surname;

    private String phoneNumber;

}
