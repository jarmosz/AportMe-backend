package com.aportme.backend.entity.dto.picture;

import lombok.Data;

@Data
public class PictureBaseDTO {

    private String pictureInBase64;

    private Boolean isProfilePicture;
}
