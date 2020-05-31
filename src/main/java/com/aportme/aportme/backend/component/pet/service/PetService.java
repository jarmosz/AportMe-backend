package com.aportme.aportme.backend.component.pet.service;

import com.aportme.aportme.backend.component.foundation.entity.FoundationInfo;
import com.aportme.aportme.backend.component.foundation.repository.FoundationInfoRepository;
import com.aportme.aportme.backend.component.pet.dto.AddPetDTO;
import com.aportme.aportme.backend.component.pet.dto.PetDTO;
import com.aportme.aportme.backend.component.pet.dto.UpdatePetDTO;
import com.aportme.aportme.backend.component.pet.entity.Pet;
import com.aportme.aportme.backend.component.pet.repository.PetRepository;
import com.aportme.aportme.backend.component.pet.repository.PictureRepository;
import com.aportme.aportme.backend.utils.UtilsService;
import com.aportme.aportme.backend.utils.dto.DTOEntity;
import com.aportme.aportme.backend.utils.dto.EntityDTOConverter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    private final PictureRepository pictureRepository;

    public Page<PetDTO> getAll(Pageable pageable) {
        Page<Pet> petsFromDB = petRepository.findAll(pageable);
        List<PetDTO> pets = petsFromDB
                                .getContent()
                                .stream()
                                .map(pet -> (PetDTO) entityDTOConverter.convertToDto(pet, new PetDTO()))
                                .collect(Collectors.toList());

        return new PageImpl<>(pets, pageable, petsFromDB.getTotalElements());
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
        dbPet.setSex(petDTO.getSex());
        dbPet.setDescription(petDTO.getDescription());
        dbPet.setDiseases(petDTO.getDiseases());
        dbPet.setName(petDTO.getName());
        dbPet.setSize(petDTO.getSize());
        dbPet.setPetType(petDTO.getPetType());
        dbPet.setTrainingNeeded(petDTO.getTrainingNeeded());

        Optional<FoundationInfo> foundationInfoFromDB = foundationInfoRepository.findById(foundationId);
        if (foundationInfoFromDB.isEmpty()) {
            throw new Exception("Foundation not found");
        }
        dbPet.setFoundationInfo(foundationInfoFromDB.get());
        dbPet = petRepository.save(dbPet);

        dbPet.setPictures(pictureService.createAll(dbPet, petDTO.getPictures()));
        return entityDTOConverter.convertToDto(dbPet, new PetDTO());
    }

    public void delete(Long id) throws Exception {
        Optional<Pet> petFromDB = petRepository.findById(id);
        if(petFromDB.isEmpty()) {
            throw new Exception("Pet with id " + id + " not found");
        }
        Pet pet = petFromDB.get();
        pictureService.delete(pictureRepository.findAllByPet(pet).stream().map(PetPicture::getId).collect(Collectors.toList()));

        petRepository.delete(pet);
    }
}
