package com.aportme.aportme.backend.dto.user.userInfo;

import com.aportme.aportme.backend.dto.address.AddressDTO;
import com.aportme.aportme.backend.dto.DTOEntity;
import com.aportme.aportme.backend.dto.pet.PetDTO;
import com.aportme.aportme.backend.dto.user.UserDTO;
import lombok.Data;

import java.util.List;

@Data
public class UserInfoDTO implements DTOEntity {

    private Long id;

    private String name;

    private String surname;

    private String phoneNumber;

    private AddressDTO address;

    private UserDTO user;

    private List<PetDTO> likedPets;
}
