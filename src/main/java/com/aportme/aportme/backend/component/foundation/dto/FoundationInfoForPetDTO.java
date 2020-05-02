package com.aportme.aportme.backend.component.foundation.dto;

import com.aportme.aportme.backend.component.user.dto.UserDTO;
import com.aportme.aportme.backend.component.address.entity.Address;
import lombok.Data;

@Data
public class FoundationInfoForPetDTO {

    private Long id;

    private String name;

    private String nip;

    private String phoneNumber;

    private String description;

    private byte[] foundationLogo;

    private UserDTO user;

    private Address address;
}
