package com.aportme.backend.repository;

import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query("select p from Pet p " +
            "where p.foundation.id = ?1 and " +
            "lower(p.searchableName) like %?2% or " +
            "lower(p.searchableBreed) like %?2% " +
            "order by p.creationDate desc ")
    Page<Pet> findAllFoundationPets(Pageable pageable, Long foundationId, String search);
}
