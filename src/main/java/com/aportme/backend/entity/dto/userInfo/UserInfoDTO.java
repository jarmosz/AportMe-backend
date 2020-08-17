package com.aportme.backend.entity.dto.userInfo;

import com.aportme.backend.entity.dto.address.AddressDTO;
import com.aportme.backend.entity.dto.pet.PetDTO;
import com.aportme.backend.entity.dto.user.UserDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserInfoDTO extends UserInfoBaseDTO {

    private Long id;

    private AddressDTO address;

    private UserDTO user;

    private List<PetDTO> likedPets;
}
