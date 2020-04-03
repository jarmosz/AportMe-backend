package com.aportme.aportme.backend.component.pet.dto.pictures;

import lombok.Data;

@Data
public class AddPetPictureDTO {

    private Long petId;

    private String pictureInBase64;
}
