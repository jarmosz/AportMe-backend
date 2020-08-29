package com.aportme.backend.service;

import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.Pet;
import com.aportme.backend.entity.PetPicture;
import com.aportme.backend.entity.dto.pet.AddPetDTO;
import com.aportme.backend.entity.dto.pet.PetDTO;
import com.aportme.backend.entity.dto.pet.UpdatePetDTO;
import com.aportme.backend.exception.PetNotFoundException;
import com.aportme.backend.repository.PetRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final FoundationService foundationService;
    private final PictureService pictureService;
    private final ModelMapper modelMapper;

    public Page<PetDTO> getAllPets(Pageable pageable) {
        Page<Pet> pets = petRepository.findAll(pageable);

        List<PetDTO> petDTOs = pets
                .getContent()
                .stream()
                .map(pet -> modelMapper.map(pet, PetDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(petDTOs, pageable, pets.getTotalElements());
    }

    public PetDTO getPetById(Long id) {
        Pet pet = findPetById(id);
        return modelMapper.map(pet, PetDTO.class);
    }

    public ResponseEntity<Object> update(Long id, UpdatePetDTO petDTO) {
        Pet pet = findPetById(id);
        modelMapper.map(petDTO, pet);
        petRepository.save(pet);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Object> create(Long foundationId, AddPetDTO petDTO) {
        Pet pet = modelMapper.map(petDTO, Pet.class);
        Foundation foundation = foundationService.findFoundationById(foundationId);
        pet.setFoundation(foundation);
        petRepository.save(pet);

        List<PetPicture> pictures = pictureService.createPicturesForPet(pet, petDTO.getPictures());

        pet.setPictures(pictures);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Object> deletePet(Long id) {
        Pet pet = findPetById(id);
        pictureService.deleteAllPetPictures(pet);
        petRepository.delete(pet);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public Pet findPetById(Long id) {
        return petRepository.findById(id).orElseThrow(PetNotFoundException::new);
    }

    public ResponseEntity<Object> newUpdate(Long id, UpdatePetDTO updatePet) {
        Pet pet = findPetById(id);
        pictureService.updatePetPictures(updatePet, pet);
        modelMapper.map(pet, updatePet.getPetData());
        petRepository.save(pet);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
