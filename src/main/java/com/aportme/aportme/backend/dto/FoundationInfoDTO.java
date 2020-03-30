package com.aportme.aportme.backend.dto;

import com.aportme.aportme.backend.entity.Address;
import com.aportme.aportme.backend.entity.pet.Pet;
import com.aportme.aportme.backend.entity.user.User;
import lombok.Data;

import java.util.List;

@Data
public class FoundationInfoDTO implements DTOEntity {

    private Long id;

    private String name;

    private String nip;

    private String phoneNumber;

    private User user;

    private Address address;

    private List<Pet> pets;
}
