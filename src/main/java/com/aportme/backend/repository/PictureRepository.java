package com.aportme.backend.repository;

import com.aportme.backend.entity.Pet;
import com.aportme.backend.entity.PetPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<PetPicture, Long> {
    List<PetPicture> findAllByPet(Pet pet);

    List<PetPicture> findAllByIdIn(List<Long> ids);
}
