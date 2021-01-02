package com.aportme.backend.service;

import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.Pet;
import com.aportme.backend.entity.PetPicture;
import com.aportme.backend.entity.User;
import com.aportme.backend.entity.dto.pet.*;
import com.aportme.backend.repository.PetRepository;
import com.aportme.backend.utils.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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
    private final UserService userService;
    private final CanonicalService canonicalService;
    private PictureService pictureService;

    public Page<SimplePetDTO> getPets(Pageable pageable, PetFilters filters) {
        Page<Pet> page = searchService.findPetsByFilters(pageable, filters);

        List<SimplePetDTO> content = page.getContent()
                .stream()
                .map(this::convertToSimplePetDTO)
                .collect(Collectors.toList());

        return PaginationService.mapToPageImpl(content, pageable, page.getTotalElements());
    }

    public Page<PetDTO> getFoundationPets(Pageable pageable, String query) {
        String foundationEmail = authenticationService.getLoggedUsername();
        Foundation foundation = foundationService.findByEmail(foundationEmail);
        Page<Pet> page = petRepository.findAllByFoundationAndSearchableNameIsContaining(pageable, foundation, query.toLowerCase());

        List<PetDTO> content = page.getContent()
                .stream()
                .map(pet -> modelMapper.map(pet, PetDTO.class))
                .collect(Collectors.toList());

        return PaginationService.mapToPageImpl(content, pageable, page.getTotalElements());
    }

    public PetDTO getById(Long id) {
        Pet pet = findById(id);
        PetDTO dto =  modelMapper.map(pet, PetDTO.class);
        dto.setLiked(isPetLikedByUser(pet));
        return dto;
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

    private boolean isPetLikedByUser(Pet pet) {
        Long userId = authenticationService.getLoggedUserId();
        if (userId == null) {
            return false;
        }
        User user = userService.findById(userId);
        return pet.getUsers().contains(user);
    }

    private SimplePetDTO convertToSimplePetDTO(Pet pet) {
        ModelMapperUtil.mapToSimplePetDTO(modelMapper);
        SimplePetDTO dto = modelMapper.map(pet, SimplePetDTO.class);
        PetPicture profilePicture = pictureService.findProfilePicture(pet.getPictures());

        dto.setProfilePicture(profilePicture.getPictureInBase64());
        dto.setLiked(isPetLikedByUser(pet));
        return dto;
    }

    @Autowired
    public void setPictureService(PictureService pictureService) {
        this.pictureService = pictureService;
    }
}
