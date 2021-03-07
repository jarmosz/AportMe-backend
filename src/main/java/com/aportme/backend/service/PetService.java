package com.aportme.backend.service;

import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.Pet;
import com.aportme.backend.entity.User;
import com.aportme.backend.entity.dto.pet.PetBaseDTO;
import com.aportme.backend.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final ModelMapper modelMapper;
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final CanonicalService canonicalService;

    public Pet create(PetBaseDTO dto, Foundation foundation) {
        Pet pet = modelMapper.map(dto, Pet.class);

        pet.setFoundation(foundation);
        pet.setSearchableName(canonicalService.replaceCanonicalLetters(pet.getName().toLowerCase()));
        pet.setSearchableBreed(canonicalService.replaceCanonicalLetters(pet.getBreed().toLowerCase()));

        return save(pet);
    }

    public Page<Pet> getFoundationPetsPage(Pageable pageable, Foundation foundation, String query) {
        return petRepository.findAllByFoundationAndSearchableNameIsContainingIgnoreCaseOrderByCreationDateDesc(pageable, foundation, query.toLowerCase());
    }

    public Pet findById(Long id) {
        return petRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pet not found"));
    }

    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }

    public boolean isPetLikedByUser(Pet pet) {
        Long userId = authenticationService.getLoggedUserId();
        if (userId == null) {
            return false;
        }
        User user = userService.findById(userId);
        return pet.getUsers().contains(user);
    }

    public void delete(Pet pet) {
        petRepository.delete(pet);
    }
}
