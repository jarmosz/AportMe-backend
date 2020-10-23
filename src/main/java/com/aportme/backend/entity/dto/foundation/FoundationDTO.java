package com.aportme.backend.entity.dto.foundation;

import com.aportme.backend.entity.dto.address.AddressDTO;
import com.aportme.backend.entity.dto.pet.PetDTO;
import com.aportme.backend.entity.dto.user.UserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class FoundationDTO extends FoundationBaseDTO {

    private Long id;

    private String foundationLogo;

    private String accountNumber;

    private String email;

    private AddressDTO address;

    private List<PetDTO> pets;
}
