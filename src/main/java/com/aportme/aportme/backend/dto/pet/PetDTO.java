package com.aportme.aportme.backend.dto.pet;

import com.aportme.aportme.backend.dto.DTOEntity;
import com.aportme.aportme.backend.dto.foundation.FoundationInfoDTO;
import com.aportme.aportme.backend.dto.foundation.FoundationInfoSimpleDTO;
import com.aportme.aportme.backend.dto.user.UserDTO;
import com.aportme.aportme.backend.entity.pet.enums.AgeCategory;
import com.aportme.aportme.backend.entity.pet.enums.AgeSuffix;
import com.aportme.aportme.backend.entity.pet.enums.PetSize;
import com.aportme.aportme.backend.entity.pet.enums.PetType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PetDTO implements DTOEntity {

    private Long id;

    private String name;

    private String breed;

    private int age;

    private PetType petType;

    private AgeSuffix ageSuffix;

    private AgeCategory ageCategory;

    private PetSize size;

    private String diseases;

    private String behaviorToChildren;

    private String behaviorToAnimals;

    private Boolean trainingNeeded;

    private Boolean behavioristNeeded;

    private String description;

    private FoundationInfoSimpleDTO foundationInfo;

    private List<PetPictureDTO> pictures = new ArrayList<>();

    private List<UserDTO> users = new ArrayList<>();
}
