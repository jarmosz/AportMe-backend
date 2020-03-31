package com.aportme.aportme.backend.dto.pet;

import com.aportme.aportme.backend.dto.DTOEntity;
import lombok.Data;

@Data
public class PetPictureDTO implements DTOEntity {

    private Long id;

    private String pictureInBase64;
}
