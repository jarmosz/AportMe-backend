package com.aportme.aportme.backend.component.foundation.dto;

import com.aportme.aportme.backend.component.address.dto.AddressDTO;
import com.aportme.aportme.backend.utils.dto.DTOEntity;
import com.aportme.aportme.backend.component.pet.dto.PetDTO;
import com.aportme.aportme.backend.component.user.dto.UserDTO;
import lombok.Data;

import java.util.List;

@Data
public class FoundationInfoDTO implements DTOEntity {

    private Long id;

    private String name;

    private String nip;

    private String phoneNumber;

    private UserDTO user;

    private AddressDTO address;

    private List<PetDTO> pets;
}
