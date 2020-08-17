package com.aportme.backend.entity.dto.foundation;

import com.aportme.backend.entity.dto.address.AddressDTO;
import com.aportme.backend.entity.dto.user.UserDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoundationForPetDTO extends FoundationBaseDTO {

    private Long id;

    private String foundationLogo;

    private UserDTO user;

    private AddressDTO address;
}
