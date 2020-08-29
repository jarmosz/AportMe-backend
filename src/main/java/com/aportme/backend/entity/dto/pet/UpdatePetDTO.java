package com.aportme.backend.entity.dto.pet;

import com.aportme.backend.entity.dto.picture.AddPetPictureDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdatePetDTO {

    private PetBaseDTO petData;

    private List<AddPetPictureDTO> picturesToAdd;

    private Long previousProfilePictureId;

    private Long newProfilePictureId;

    private List<Long> pictureIdsToDelete;
}
