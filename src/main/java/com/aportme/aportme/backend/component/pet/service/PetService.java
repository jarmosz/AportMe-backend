package com.aportme.aportme.backend.component.pet.service;

import com.aportme.aportme.backend.utils.dto.DTOEntity;
import com.aportme.aportme.backend.component.pet.dto.AddPetDTO;
import com.aportme.aportme.backend.component.pet.dto.PetDTO;
import com.aportme.aportme.backend.component.pet.dto.UpdatePetDTO;
import com.aportme.aportme.backend.component.foundation.entity.FoundationInfo;
import com.aportme.aportme.backend.component.pet.entity.Pet;
import com.aportme.aportme.backend.component.foundation.repository.FoundationInfoRepository;
import com.aportme.aportme.backend.component.pet.repository.PetRepository;
import com.aportme.aportme.backend.utils.dto.EntityDTOConverter;
import com.aportme.aportme.backend.utils.UtilsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final FoundationInfoRepository foundationInfoRepository;
    private final EntityDTOConverter entityDTOConverter;
    private final PictureService pictureService;

    public List<DTOEntity> getAll() {
        return petRepository.findAll()
                .stream()
                .map((pet -> entityDTOConverter.convertToDto(pet, new PetDTO())))
                .collect(Collectors.toList());
    }

    public DTOEntity getById(Long id) {
        Optional<Pet> petFromDB = petRepository.findById(id);
        if (petFromDB.isEmpty()) {
            return null;
        }
        return entityDTOConverter.convertToDto(petFromDB.get(), new PetDTO());
    }

    public DTOEntity update(Long id, UpdatePetDTO petDTO) throws Exception {
        Optional<Pet> petFromDB = petRepository.findById(id);
        if (petFromDB.isEmpty()) {
            throw new Exception("Pet not found");
        }
        Pet dbPet = petFromDB.get();
        Pet convertedDTO = (Pet) entityDTOConverter.convertToEntity(new Pet(), petDTO);
        UtilsService.copyNonNullProperties(convertedDTO, dbPet);
        return entityDTOConverter.convertToDto(petRepository.save(dbPet), new PetDTO());
    }

    public DTOEntity create(Long foundationId, AddPetDTO petDTO) throws Exception {
        Pet dbPet = new Pet();
        dbPet.setAge(petDTO.getAge());
        dbPet.setAgeCategory(petDTO.getAgeCategory());
        dbPet.setAgeSuffix(petDTO.getAgeSuffix());
        dbPet.setBehavioristNeeded(petDTO.getBehavioristNeeded());
        dbPet.setBehaviorToAnimals(petDTO.getBehaviorToAnimals());
        dbPet.setBehaviorToChildren(petDTO.getBehaviorToChildren());
        dbPet.setBreed(petDTO.getBreed());
        dbPet.setDescription(petDTO.getDescription());
        dbPet.setDiseases(petDTO.getDiseases());
        dbPet.setName(petDTO.getName());
        dbPet.setSize(petDTO.getSize());
        dbPet.setPetType(petDTO.getPetType());
        dbPet.setTrainingNeeded(petDTO.getTrainingNeeded());

        dbPet.setPictures(pictureService.createAll(petDTO.getPictures()));

        Optional<FoundationInfo> foundationInfoFromDB = foundationInfoRepository.findById(foundationId);
        if (foundationInfoFromDB.isEmpty()) {
            throw new Exception("Foundation not found");
        }
        dbPet.setFoundationInfo(foundationInfoFromDB.get());

        return entityDTOConverter.convertToDto(petRepository.save(dbPet), new PetDTO());
    }
}