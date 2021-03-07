package com.aportme.backend.entity.dto.pet;

import com.aportme.backend.entity.dto.picture.PictureBaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class AddPetDTO extends PetBaseDTO {

    private List<PictureBaseDTO> petPictures = new ArrayList<>();

}
