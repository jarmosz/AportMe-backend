package com.aportme.backend.component.pet.dto.pictures;

import com.aportme.backend.utils.dto.DTOEntity;
import lombok.Data;

@Data
public class PetPictureDTO implements DTOEntity {

    private Long id;

    private String pictureInBase64;

    private boolean isProfilePicture;
}
