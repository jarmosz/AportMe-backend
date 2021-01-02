package com.aportme.backend.entity.dto.pet;

import com.aportme.backend.entity.enums.AgeCategory;
import com.aportme.backend.entity.enums.PetSex;
import com.aportme.backend.entity.enums.PetSize;
import com.aportme.backend.entity.enums.PetType;
import lombok.Data;

@Data
public class PetFilters {
    private AgeCategory ageCategory;
    private PetType petType;
    private PetSize size;
    private PetSex petSex;
    private Boolean onlyLikedPets;
    private String searchBreedQuery;
    private String searchNameQuery;
}
