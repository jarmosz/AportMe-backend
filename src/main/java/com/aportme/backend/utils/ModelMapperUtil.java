package com.aportme.backend.utils;

import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.Pet;
import com.aportme.backend.entity.dto.foundation.FoundationDTO;
import com.aportme.backend.entity.dto.pet.PetDTO;
import org.modelmapper.ModelMapper;

public class ModelMapperUtil {

    public static void mapUserToFoundationDTO(ModelMapper modelMapper) {
        modelMapper.typeMap(Foundation.class, FoundationDTO.class)
                .addMappings(mapper -> mapper.map(src -> src.getUser().getEmail(), FoundationDTO::setEmail));
    }

    public static void mapPetDTOtoPet(ModelMapper modelMapper) {
        modelMapper.typeMap(PetDTO.class, Pet.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getName().toLowerCase(), Pet::setSearchableName);
                    mapper.map(src -> src.getBreed().toLowerCase(), Pet::setSearchableBreed);
                });
    }
}
