package com.aportme.backend.entity.dto.foundation;

import com.aportme.backend.entity.dto.address.AddressDTO;
import com.aportme.backend.entity.dto.user.UserDTO;
import com.aportme.backend.entity.dto.pet.PetDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FoundationDTO extends FoundationBaseDTO {

    private Long id;

    private String foundationLogo;

    private String accountNumber;

    private UserDTO user;

    private AddressDTO address;

    private List<PetDTO> pets;
}
