package com.aportme.backend.entity.dto.picture;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UploadPictureDTO extends PictureBaseDTO {

    private Long petId;
}
