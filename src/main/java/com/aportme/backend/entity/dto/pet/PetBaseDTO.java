package com.aportme.backend.entity.dto.pet;

import com.aportme.backend.entity.enums.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class PetBaseDTO {

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
