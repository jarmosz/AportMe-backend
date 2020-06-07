package com.aportme.backend.component.userInfo.dto;

import com.aportme.backend.utils.dto.DTOEntity;
import lombok.Data;

@Data
public class UpdateUserInfoDTO implements DTOEntity {

    private String name;

    private String surname;

    private String phoneNumber;
}