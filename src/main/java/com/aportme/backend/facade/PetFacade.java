package com.aportme.backend.facade;

import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.Pet;
import com.aportme.backend.entity.PetPicture;
import com.aportme.backend.entity.dto.pet.*;
import com.aportme.backend.service.*;
import com.aportme.backend.service.survey.UserSurveyService;
import com.aportme.backend.utils.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
@RequiredArgsConstructor
public class PetFacade {

    private final ModelMapper modelMapper;
    private final PetService petService;
    private final SearchService searchService;
    private final PetPictureService petPictureService;
    private final FoundationService foundationService;
    private final AuthenticationService authenticationService;
    private final CanonicalService canonicalService;
    private final UserSurveyService userSurveyService;

    public Page<SimplePetDTO> getPets(Pageable pageable, PetFilters filters) {
        Page<Pet> page = searchService.findPetsByFilters(pageable, filters);

        List<SimplePetDTO> content = page.getContent()
                .stream()
                .map(this::convertToSimplePetDTO)
                .collect(Collectors.toList());

        return PaginationService.mapToPageImpl(content, pageable, page.getTotalElements());
    }

    public PetDTO create(AddPetDTO dto) {
        String email = authenticationService.getLoggedUsername();
        Foundation foundation = foundationService.findByEmail(email);
        Pet pet = petService.create(dto, foundation);

        List<PetPicture> pictures = dto.getPetPictures().stream()
                .map(pic -> petPictureService.createPicture(pic, pet))
                .collect(Collectors.toList());

        pet.setPictures(pictures);
        return modelMapper.map(pet, PetDTO.class);
    }

    public Page<PetDTO> getFoundationPets(Pageable pageable, String query) {
        String foundationEmail = authenticationService.getLoggedUsername();
        Foundation foundation = foundationService.findByEmail(foundationEmail);
        Page<Pet> page = petService.getFoundationPetsPage(pageable, foundation, query);

        List<PetDTO> content = page.getContent()
                .stream()
                .map(pet -> modelMapper.map(pet, PetDTO.class))
                .collect(Collectors.toList());

        return PaginationService.mapToPageImpl(content, pageable, page.getTotalElements());
    }

    public PetDTO getById(Long id) {
        Pet pet = petService.findById(id);
        boolean isLiked = petService.isPetLikedByUser(pet);

        PetDTO dto = modelMapper.map(pet, PetDTO.class);
        dto.setLiked(isLiked);

        return dto;
    }

    public PetBaseDTO update(Long id, PetBaseDTO dto) {
        Pet pet = petService.findById(id);

        modelMapper.map(dto, pet);
        pet.setSearchableName(canonicalService.replaceCanonicalLetters(dto.getName().toLowerCase()));
        pet.setSearchableBreed(canonicalService.replaceCanonicalLetters(dto.getBreed().toLowerCase()));
        petService.save(pet);

        return modelMapper.map(pet, PetBaseDTO.class);
    }

    public void delete(Long id) {
        Pet pet = petService.findById(id);

        pet.getUsers().forEach(user -> user.getLikedPets().remove(pet));

        userSurveyService.deleteAllByPet(pet);
        petPictureService.deleteAll(pet);
        petService.delete(pet);
    }


    private SimplePetDTO convertToSimplePetDTO(Pet pet) {
        ModelMapperUtil.mapToSimplePetDTO(modelMapper);
        SimplePetDTO dto = modelMapper.map(pet, SimplePetDTO.class);
        PetPicture profilePicture = petPictureService.findProfilePicture(pet.getPictures());
        boolean isLiked = petService.isPetLikedByUser(pet);

        dto.setDownloadUrl(profilePicture.getDownloadUrl());
        dto.setLiked(isLiked);

        return dto;
    }
}
