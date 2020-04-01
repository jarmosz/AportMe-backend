package com.aportme.aportme.backend.dto.foundation;

import com.aportme.aportme.backend.dto.user.UserDTO;
import com.aportme.aportme.backend.entity.Address;
import lombok.Data;

@Data
public class FoundationInfoForPetDTO {

    private Long id;

    private String name;

    private String nip;

    private String phoneNumber;

    private UserDTO user;

    private Address address;
}
