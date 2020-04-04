package com.aportme.aportme.backend.component.pet.dto.pictures;

import com.aportme.aportme.backend.utils.dto.DTOEntity;
import lombok.Data;

@Data
public class PetPictureDTO implements DTOEntity {

    private Long id;

    private String pictureInBase64;
}
