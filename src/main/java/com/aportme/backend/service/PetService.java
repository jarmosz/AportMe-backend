package com.aportme.backend.service;

import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.Pet;
import com.aportme.backend.entity.PetPicture;
import com.aportme.backend.entity.dto.pet.AddPetDTO;
import com.aportme.backend.entity.dto.pet.PetBaseDTO;
import com.aportme.backend.entity.dto.pet.PetDTO;
import com.aportme.backend.entity.dto.pet.PetFilters;
import com.aportme.backend.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final FoundationService foundationService;
    private final ModelMapper modelMapper;
    private final AuthenticationService authenticationService;
    private final SearchService searchService;
    private final CanonicalService canonicalService;
    private PictureService pictureService;

    @Transactional
    public Page<PetDTO> getPets(Pageable pageable, PetFilters filters) {
        Page<Pet> petsPage = searchService.findPetsByFilters(pageable, filters);
        return convertPetsToPage(pageable, petsPage);
    }

    public PetDTO getById(Long id) {
        Pet pet = findById(id);
        return modelMapper.map(pet, PetDTO.class);
    }

    public PetDTO update(Long id, PetBaseDTO petDTO) {
        Pet pet = findById(id);
        modelMapper.map(petDTO, pet);
        Pet updatedPet = petRepository.save(pet);
        return modelMapper.map(updatedPet, PetDTO.class);
    }

    public PetDTO create(AddPetDTO petDTO) {
        Pet pet = modelMapper.map(petDTO, Pet.class);
        String email = authenticationService.getLoggedUsername();
        Foundation foundation = foundationService.findByEmail(email);
        pet.setFoundation(foundation);
        pet.setSearchableName(canonicalService.replaceCanonicalLetters(pet.getName().toLowerCase()));
        pet.setSearchableBreed(canonicalService.replaceCanonicalLetters(pet.getBreed().toLowerCase()));
        pet = petRepository.save(pet);

        List<PetPicture> pictures = pictureService.createPicturesForNewPet(pet, petDTO.getPictures());

        pet.setPictures(pictures);
        return modelMapper.map(pet, PetDTO.class);
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

    private Page<PetDTO> convertPetsToPage(Pageable pageable, Page<Pet> pets) {
        List<PetDTO> petDTOs = pets.getContent()
                .stream()
                .map(pet -> modelMapper.map(pet, PetDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(petDTOs, pageable, pets.getTotalElements());
    }

    @Autowired
    public void setPictureService(PictureService pictureService) {
        this.pictureService = pictureService;
    }
}
