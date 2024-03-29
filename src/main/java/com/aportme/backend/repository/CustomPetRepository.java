package com.aportme.backend.repository;

import com.aportme.backend.entity.Pet;
import com.aportme.backend.entity.User;
import com.aportme.backend.entity.dto.pet.PetFilters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomPetRepository {
    Page<Pet> findByFilters(Pageable pageable, String name, String breed, PetFilters filters, User user);
}
