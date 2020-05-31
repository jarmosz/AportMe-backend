package com.aportme.backend.component.foundation.dto;

import com.aportme.backend.component.address.dto.AddressDTO;
import com.aportme.backend.utils.dto.DTOEntity;
import com.aportme.backend.component.pet.dto.PetDTO;
import com.aportme.backend.component.user.dto.UserDTO;
import lombok.Data;

import java.util.List;

@Data
public class FoundationInfoDTO implements DTOEntity {

    private Long id;

    private String name;

    private String nip;

    private String phoneNumber;

    private String description;

    private String foundationLogo;

    private UserDTO user;

    private AddressDTO address;

    private List<PetDTO> pets;
}
