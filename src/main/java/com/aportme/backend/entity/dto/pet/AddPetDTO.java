package com.aportme.backend.entity.dto.pet;

import com.aportme.backend.entity.dto.picture.AddPetPictureDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class AddPetDTO extends PetBaseDTO {

    private List<AddPetPictureDTO> pictures = new ArrayList<>();

}
