package com.aportme.backend.component.foundation.dto;

import com.aportme.backend.component.address.entity.Address;
import com.aportme.backend.component.user.dto.UserDTO;
import com.aportme.backend.utils.dto.DTOEntity;
import lombok.Data;

@Data
public class FoundationInfoWithoutPetsDTO implements DTOEntity {

    private Long id;

    private String name;

    private String nip;

    private String phoneNumber;

    private String description;

    private String foundationLogo;

    private UserDTO user;

    private Address address;
}
