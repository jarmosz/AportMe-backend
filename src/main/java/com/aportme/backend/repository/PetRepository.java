package com.aportme.backend.repository;

import com.aportme.backend.entity.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    Page<Pet> findAllByNameContainingIgnoreCaseAndFoundation_Id(Pageable pageable, String name, Long id);

    Page<Pet> findAllByFoundation_Id(Pageable pageable, Long id);
}
