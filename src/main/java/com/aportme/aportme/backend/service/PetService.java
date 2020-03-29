package com.aportme.aportme.backend.service;

import com.aportme.aportme.backend.entity.pet.Pet;
import com.aportme.aportme.backend.repository.FoundationInfoRepository;
import com.aportme.aportme.backend.repository.PetRepository;
import com.aportme.aportme.backend.utils.UtilsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final FoundationInfoRepository foundationInfoRepository;

    public List<Pet> getAll() {
        return petRepository.findAll();
    }

    public Pet getById(Long id) {
        return petRepository.findById(id).orElse(null);
    }

    public Pet update(Long id, Pet pet) {
        Pet dbPet = petRepository.findById(id).get();
        UtilsService.copyNonNullProperties(pet, dbPet);
        return petRepository.save(dbPet);
    }

    public Pet create(Long foundationId, Pet pet) {
        Pet dbPet = new Pet();
        dbPet.setAge(pet.getAge());
        dbPet.setAgeCategory(pet.getAgeCategory());
        dbPet.setAgeSuffix(pet.getAgeSuffix());
        dbPet.setBehavioristNeeded(pet.getBehavioristNeeded());
        dbPet.setBehaviorToAnimals(pet.getBehaviorToAnimals());
        dbPet.setBehaviorToChildren(pet.getBehaviorToChildren());
        dbPet.setBreed(pet.getBreed());
        dbPet.setDescription(pet.getDescription());
        dbPet.setDiseases(pet.getDiseases());
        dbPet.setFoundationInfo(foundationInfoRepository.findById(foundationId).get());
        dbPet.setName(pet.getName());
        dbPet.setSize(pet.getSize());
        dbPet.setPetType(pet.getPetType());
        dbPet.setTrainingNeeded(pet.getTrainingNeeded());
        return petRepository.save(dbPet);
    }
}
