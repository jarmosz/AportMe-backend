package com.aportme.backend.component.pet.dto.pictures;

import lombok.Data;

@Data
public class AddPetPictureDTO {

    private String pictureInBase64;

    private Boolean isProfilePicture;
}
