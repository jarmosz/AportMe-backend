package com.aportme.aportme.backend.dto;

import com.aportme.aportme.backend.entity.pet.Pet;
import lombok.Data;

@Data
public class PetPictureDTO implements DTOEntity {

    private Long id;

    private String pictureInBase64;

    private Pet pet;
}
