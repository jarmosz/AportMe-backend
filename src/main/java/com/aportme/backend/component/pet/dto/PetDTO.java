package com.aportme.backend.component.pet.dto;

import com.aportme.backend.component.pet.enums.*;
import com.aportme.backend.utils.dto.DTOEntity;
import com.aportme.backend.component.foundation.dto.FoundationInfoForPetDTO;
import com.aportme.backend.component.pet.dto.pictures.PetPictureDTO;
import com.aportme.backend.component.user.dto.UserDTO;
import lombok.Data;

import java.time.LocalDateTime;
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

    private PetSex sex;

    private Boolean trainingNeeded;

    private Boolean behavioristNeeded;

    private String description;

    private FoundationInfoForPetDTO foundationInfo;

    private LocalDateTime creationDate;

    private LocalDateTime updateDate;

    private List<PetPictureDTO> pictures = new ArrayList<>();

    private List<UserDTO> users = new ArrayList<>();
}
