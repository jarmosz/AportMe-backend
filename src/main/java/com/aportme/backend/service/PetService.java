package com.aportme.backend.service;

import com.aportme.backend.entity.*;
import com.aportme.backend.entity.dto.pet.AddPetDTO;
import com.aportme.backend.entity.dto.pet.PetBaseDTO;
import com.aportme.backend.entity.dto.pet.PetDTO;
import com.aportme.backend.entity.dto.pet.PetFilters;
import com.aportme.backend.entity.enums.Role;
import com.aportme.backend.repository.PetRepository;
import com.aportme.backend.repository.SearchPetRepository;
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
    private final SearchPetRepository searchPetRepository;
    private final FoundationService foundationService;
    private final ModelMapper modelMapper;
    private final AuthenticationService authenticationService;
    private PictureService pictureService;

    public Page<PetDTO> getPets(Pageable pageable, String searchQuery, PetFilters filters, boolean isFoundationCall) {
        SearchablePet searchablePet = resolveSearchQuery(searchQuery);
        Long foundationId = authenticationService.getLoggedUserId();
        isFoundationCall = verifyFoundationCall(isFoundationCall, foundationId);
        Page<Pet> petsPage = searchPetRepository.findPetsByNameAndBreed(
                pageable,
                searchablePet.getName(),
                searchablePet.getBreed(),
                filters,
                isFoundationCall,
                foundationId);

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

    public ResponseEntity<Object> create(AddPetDTO petDTO) {
        Pet pet = modelMapper.map(petDTO, Pet.class);
        String email = authenticationService.getLoggedUserName();
        Foundation foundation = foundationService.findByEmail(email);
        pet.setFoundation(foundation);
        pet.setSearchableName(pet.getName().toLowerCase());
        pet.setSearchableBreed(pet.getBreed().toLowerCase());
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

    private Page<PetDTO> convertPetsToPage(Pageable pageable, Page<Pet> pets) {
        List<PetDTO> petDTOs = pets.getContent()
                .stream()
                .map(pet -> modelMapper.map(pet, PetDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(petDTOs, pageable, pets.getTotalElements());
    }

    private SearchablePet resolveSearchQuery(String query) {
        if (query == null || query.isBlank()) {
            return new SearchablePet("", "");
        } else if (!query.contains(",")) {
            return new SearchablePet(query.toLowerCase(), "");
        } else {
            String[] splittedQuery = query.split(",");
            return new SearchablePet(splittedQuery[0].toLowerCase(), splittedQuery[1].toLowerCase());
        }
    }

    private boolean verifyFoundationCall(boolean isFoundatioCall, Long foundationId) {
        if(isFoundatioCall) {
            return foundationId != null && authenticationService.getAuthorities().contains(Role.FOUNDATION);
        }
        return false;
    }

    @Autowired
    public void setPictureService(PictureService pictureService) {
        this.pictureService = pictureService;
    }
}
