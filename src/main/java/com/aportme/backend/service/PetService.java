package com.aportme.backend.service;

import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.Pet;
import com.aportme.backend.entity.PetPicture;
import com.aportme.backend.entity.dto.pet.AddPetDTO;
import com.aportme.backend.entity.dto.pet.PetDTO;
import com.aportme.backend.entity.dto.pet.UpdatePetDTO;
import com.aportme.backend.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final FoundationService foundationService;
    private final ModelMapper modelMapper;
    private PictureService pictureService;

    public Page<PetDTO> getPets(Pageable pageable) {
        Page<Pet> pets = petRepository.findAll(pageable);

        List<PetDTO> petDTOs = pets
                .getContent()
                .stream()
                .map(pet -> modelMapper.map(pet, PetDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(petDTOs, pageable, pets.getTotalElements());
    }

    public PetDTO getById(Long id) {
        Pet pet = findById(id);
        return modelMapper.map(pet, PetDTO.class);
    }

    public PetDTO update(Long id, UpdatePetDTO petDTO) {
        Pet pet = findById(id);
        modelMapper.map(petDTO, pet);
        Pet updatedPet = petRepository.save(pet);
        return modelMapper.map(updatedPet, PetDTO.class);
    }

    //TODO We should get foundation from Spring Security Context
    public ResponseEntity<Object> create(Long foundationId, AddPetDTO petDTO) {
        Pet pet = modelMapper.map(petDTO, Pet.class);
        Foundation foundation = foundationService.findById(foundationId);
        pet.setFoundation(foundation);
        petRepository.save(pet);

        List<PetPicture> pictures = pictureService.createPicturesForNewPet(pet, petDTO.getPictures());

        pet.setPictures(pictures);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Object> delete(Long id) {
        Pet pet = findById(id);
        pictureService.deleteAll(pet);
        petRepository.delete(pet);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public Pet findById(Long id) {
        return petRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pet not found"));
    }

    @Autowired
    public void setPictureService(PictureService pictureService) {
        this.pictureService = pictureService;
    }
}
