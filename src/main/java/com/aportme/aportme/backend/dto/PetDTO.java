package com.aportme.aportme.backend.dto;

import com.aportme.aportme.backend.entity.foundation.FoundationInfo;
import com.aportme.aportme.backend.entity.pet.PetPicture;
import com.aportme.aportme.backend.entity.pet.enums.AgeCategory;
import com.aportme.aportme.backend.entity.pet.enums.AgeSuffix;
import com.aportme.aportme.backend.entity.pet.enums.PetSize;
import com.aportme.aportme.backend.entity.pet.enums.PetType;
import com.aportme.aportme.backend.entity.user.User;
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

    private FoundationInfo foundationInfo;

    private List<PetPicture> pictures = new ArrayList<>();

    private List<User> users = new ArrayList<>();
}
