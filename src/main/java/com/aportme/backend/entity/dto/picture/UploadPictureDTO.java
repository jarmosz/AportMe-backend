package com.aportme.backend.entity.dto.picture;

import lombok.Data;

import javax.persistence.Lob;

@Data
public class UploadPictureDTO {
    @Lob
    private String base64Picture;

}
