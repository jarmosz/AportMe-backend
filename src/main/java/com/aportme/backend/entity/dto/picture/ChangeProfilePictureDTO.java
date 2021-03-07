package com.aportme.backend.entity.dto.picture;

import lombok.Data;

@Data
public class ChangeProfilePictureDTO {

    private Long oldProfilePictureId;

    private Long newProfilePictureId;
}
