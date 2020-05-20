package com.aportme.aportme.backend.component.pet.dto;

import com.aportme.aportme.backend.component.pet.enums.*;
import lombok.Data;

@Data
public class AddPetDTO {

    private String name;

    private String breed;

    private int age;

    private PetType petType;

    private AgeSuffix ageSuffix;

    private AgeCategory ageCategory;

    private PetSize size;

    private PetSex sex;

    private String diseases;

    private String behaviorToChildren;

    private String behaviorToAnimals;

    private Boolean trainingNeeded;

    private Boolean behavioristNeeded;

    private String description;

}
