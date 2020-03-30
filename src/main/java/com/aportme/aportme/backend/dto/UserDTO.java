package com.aportme.aportme.backend.dto;

import com.aportme.aportme.backend.entity.pet.Pet;
import com.aportme.aportme.backend.entity.user.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO implements DTOEntity {

    private Long id;

    private String email;

    private Role role;

    private List<Pet> likedPets;
}
