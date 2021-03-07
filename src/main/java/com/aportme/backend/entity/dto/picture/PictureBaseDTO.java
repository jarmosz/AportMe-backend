package com.aportme.backend.entity.dto.picture;

import lombok.Data;

@Data
public class PictureBaseDTO {

    private String base64Picture = "";

    private Boolean isProfilePicture = false;
}
