package com.aportme.backend.component.pet.dto;

import com.aportme.aportme.backend.component.pet.enums.*;
import com.aportme.backend.component.pet.enums.*;
import com.aportme.backend.utils.dto.DTOEntity;
import lombok.Data;

@Data
public class UpdatePetDTO implements DTOEntity {

    private String name;

    private String breed;

    private int age;

    private PetType petType;

    private PetSex sex;

    private AgeSuffix ageSuffix;

    private AgeCategory ageCategory;

    private PetSize size;

    private String diseases;

    private String behaviorToChildren;

    private String behaviorToAnimals;

    private Boolean trainingNeeded;

    private Boolean behavioristNeeded;

    private String description;

}
