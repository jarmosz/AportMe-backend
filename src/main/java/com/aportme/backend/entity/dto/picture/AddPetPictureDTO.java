package com.aportme.backend.entity.dto.picture;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AddPetPictureDTO {

    private MultipartFile picture;

    private Boolean isProfilePicture;
}
