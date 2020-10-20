package com.aportme.backend.repository;

import com.aportme.backend.entity.Pet;
import com.aportme.backend.entity.dto.pet.PetFilters;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SearchPetRepository implements CustomPetRepository {
    private final EntityManager entityManager;

    @Override
    public Page<Pet> findPetsByNameAndBreed(Pageable pageable, String name, String breed, PetFilters filters) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pet> criteriaQuery = criteriaBuilder.createQuery(Pet.class);

        Root<Pet> pet = criteriaQuery.from(Pet.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.like(pet.get("searchableName"), "%" + name + "%"));
        predicates.add(criteriaBuilder.like(pet.get("searchableBreed"), "%" + breed + "%"));
        if (filters != null && filters.getSize() != null) predicates.add(criteriaBuilder.equal(pet.get("size"), filters.getSize()));
        if (filters != null && filters.getAgeCategory() != null) predicates.add(criteriaBuilder.equal(pet.get("ageCategory"), filters.getAgeCategory()));
        if (filters != null && filters.getPetType() != null) predicates.add(criteriaBuilder.equal(pet.get("petType"), filters.getPetType()));
        if (filters != null && filters.getPetSex() != null) predicates.add(criteriaBuilder.equal(pet.get("petSex"), filters.getPetSex()));
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));

        TypedQuery<Pet> query = entityManager.createQuery(criteriaQuery);

        int totalRows = query.getResultList().size();
        return new PageImpl<>(query.getResultList(), pageable, totalRows);
    }
}
