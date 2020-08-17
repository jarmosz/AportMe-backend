package com.aportme.backend.entity.dto.pet;

import com.aportme.backend.entity.dto.picture.AddPetPictureDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AddPetDTO extends PetBaseDTO {

    private List<AddPetPictureDTO> pictures = new ArrayList<>();

}
