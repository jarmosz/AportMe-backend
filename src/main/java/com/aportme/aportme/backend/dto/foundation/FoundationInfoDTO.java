package com.aportme.aportme.backend.dto.foundation;

import com.aportme.aportme.backend.dto.DTOEntity;
import com.aportme.aportme.backend.dto.pet.PetDTO;
import com.aportme.aportme.backend.dto.user.UserDTO;
import com.aportme.aportme.backend.entity.Address;
import lombok.Data;

import java.util.List;

@Data
public class FoundationInfoDTO implements DTOEntity {

    private Long id;

    private String name;

    private String nip;

    private String phoneNumber;

    private UserDTO user;

    private Address address;

    private List<PetDTO> pets;
}
