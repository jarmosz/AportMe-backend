package com.aportme.aportme.backend.service;

import com.aportme.aportme.backend.entity.pet.Pet;
import com.aportme.aportme.backend.repository.PetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PetService {

    private final PetRepository petRepository;

    public List<Pet> getAll() {
        return petRepository.findAll();
    }

    public Pet getById(Long id) {
        return petRepository.findById(id).orElse(null);
    }
}
