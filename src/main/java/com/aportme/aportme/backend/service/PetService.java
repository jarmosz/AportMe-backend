package com.aportme.aportme.backend.service;

import com.aportme.aportme.backend.dto.DTOEntity;
import com.aportme.aportme.backend.dto.pet.PetDTO;
import com.aportme.aportme.backend.dto.pet.PetSimpleDTO;
import com.aportme.aportme.backend.entity.foundation.FoundationInfo;
import com.aportme.aportme.backend.entity.pet.Pet;
import com.aportme.aportme.backend.repository.FoundationInfoRepository;
import com.aportme.aportme.backend.repository.PetRepository;
import com.aportme.aportme.backend.utils.EntityDTOConverter;
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

    public DTOEntity update(Long id, PetSimpleDTO petSimpleDTO) throws Exception {
        Optional<Pet> petFromDB = petRepository.findById(id);
        if (petFromDB.isEmpty()) {
            throw new Exception("Pet not found");
        }
        Pet dbPet = petFromDB.get();
        Pet convertedDTO = (Pet) entityDTOConverter.convertToEntity(new Pet(), petSimpleDTO);
        UtilsService.copyNonNullProperties(convertedDTO, dbPet);
        return entityDTOConverter.convertToDto(petRepository.save(dbPet), new PetSimpleDTO());
    }

    public DTOEntity create(Long foundationId, PetDTO petDTO) throws Exception {
        Pet petToDB = new Pet();
        petToDB.setAge(petDTO.getAge());
        petToDB.setAgeCategory(petDTO.getAgeCategory());
        petToDB.setAgeSuffix(petDTO.getAgeSuffix());
        petToDB.setBehavioristNeeded(petDTO.getBehavioristNeeded());
        petToDB.setBehaviorToAnimals(petDTO.getBehaviorToAnimals());
        petToDB.setBehaviorToChildren(petDTO.getBehaviorToChildren());
        petToDB.setBreed(petDTO.getBreed());
        petToDB.setDescription(petDTO.getDescription());
        petToDB.setDiseases(petDTO.getDiseases());
        petToDB.setName(petDTO.getName());
        petToDB.setSize(petDTO.getSize());
        petToDB.setPetType(petDTO.getPetType());
        petToDB.setTrainingNeeded(petDTO.getTrainingNeeded());
        Optional<FoundationInfo> foundationInfoFromDB = foundationInfoRepository.findById(foundationId);
        if (foundationInfoFromDB.isEmpty()) {
            throw new Exception("Foundation not found");
        }
        petToDB.setFoundationInfo(foundationInfoFromDB.get());
        return entityDTOConverter.convertToDto(petRepository.save(petToDB), new PetDTO());
    }
}
