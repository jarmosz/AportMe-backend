package com.aportme.backend.entity.dto.picture;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PictureBaseDTO {

    private String pictureInBase64;

    private boolean profilePicture;
}
