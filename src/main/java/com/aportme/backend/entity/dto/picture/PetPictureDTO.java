package com.aportme.backend.entity.dto.picture;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PetPictureDTO extends PictureBaseDTO {

    private Long id;

    private String downloadUrl;
}
