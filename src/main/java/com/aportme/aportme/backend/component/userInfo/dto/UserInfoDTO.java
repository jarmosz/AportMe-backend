package com.aportme.aportme.backend.component.userInfo.dto;

import com.aportme.aportme.backend.component.address.dto.AddressDTO;
import com.aportme.aportme.backend.utils.dto.DTOEntity;
import com.aportme.aportme.backend.component.pet.dto.PetDTO;
import com.aportme.aportme.backend.component.user.dto.UserDTO;
import lombok.AllArgsConstructor;
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
